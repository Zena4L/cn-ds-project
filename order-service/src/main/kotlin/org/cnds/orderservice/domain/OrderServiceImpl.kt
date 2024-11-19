package org.cnds.orderservice.domain

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.flux
import org.cnds.orderservice.dtos.OrderAcceptedMessage
import org.cnds.orderservice.dtos.OrderDispatchMessage
import org.cnds.orderservice.dtos.OrderRequest
import org.cnds.orderservice.dtos.Product
import org.cnds.orderservice.product.ProductClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val productClient: ProductClient,
    private val streamBridge: StreamBridge
) :
    OrderService {


    override fun getAllOrders() = orderRepository.findAll()

    @Transactional
    override suspend fun submitOrder(orderRequest: OrderRequest): Order {
        val order = productClient.getProduct(orderRequest.productId)
            .map { product -> buildAcceptOrder(product, orderRequest.quantity) }
            .defaultIfEmpty(
                buildRejectOrder(
                    orderRequest.productId,
                    orderRequest.quantity,
                    orderRequest.productName,
                    orderRequest.productPrice
                )
            )
            .doOnNext(this::publishOrderAcceptedEvent).awaitSingle()

        return orderRepository.save(order)
    }

//    override fun consumeOrderDispatchedEvent(it: Flow<OrderDispatchMessage>): Flow<Order> {
//        return it
//            .map { message -> orderRepository.findById(message.orderId.toInt())!! }
//            .map { order -> buildDispatchOrder(order) }
//            .map { order -> orderRepository.save(order) }
//    }

    override fun consumeOrderDispatchedEvent(it: Flow<OrderDispatchMessage>): Flow<Order> {
        return it
            .map { message ->
                logger.info("Received OrderDispatchedMessage: ${message.orderId}")
                orderRepository.findById(message.orderId.toInt())
                    ?: throw IllegalStateException("Order not found: ${message.orderId}")
            }
            .map { order ->
                logger.info("Dispatching Order: ${order.id}")
                buildDispatchOrder(order)
            }
            .map { order ->
                val savedOrder = orderRepository.save(order)
                logger.info("Order saved with DISPATCHED status: ${savedOrder.id}")
                savedOrder
            }
    }


    override fun publishOrderAcceptedEvent(order: Order) {
        if (order.orderStatus != OrderStatus.ACCEPTED) return
        println(order)
        val orderAcceptedMessage = OrderAcceptedMessage(order.productId.toLong())
        logger.info("Sending order accepted event with id: $orderAcceptedMessage")
        val result = streamBridge.send("orderAccepted-out-0", orderAcceptedMessage)

        logger.info("Result of sending data for order with id: ${order.id}, status: $result")
    }

    private fun buildDispatchOrder(existingOrder: Order): Order {
        return Order(
            id = existingOrder.id,
            productName = existingOrder.productName,
            quantity = existingOrder.quantity,
            productPrice = existingOrder.productPrice,
            productId = existingOrder.productId,
            orderStatus = OrderStatus.DISPATCHED,
            version = existingOrder.version,
            createdAt = existingOrder.createdAt,
            updatedAt = existingOrder.updatedAt
        )
    }

    companion object {
        fun buildRejectOrder(
            productId: Int,
            quantity: Int,
            productName: String,
            productPrice: Int
        ): Order {
            return Order.of(
                productName = productName,
                productId = productId,
                productPrice = productPrice,
                quantity = quantity,
                orderStatus = OrderStatus.REJECTED
            )
        }

        @JvmStatic
        val logger: Logger = LoggerFactory.getLogger(OrderServiceImpl::class.java)
    }

    fun buildAcceptOrder(product: Product, quantity: Int): Order {
        return Order.of(
            productName = product.name,
            orderStatus = OrderStatus.ACCEPTED,
            productId = product.id,
            quantity = quantity,
            productPrice = product.price.toInt()
        )
    }

}