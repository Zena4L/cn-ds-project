package org.cnds.orderservice.domain

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.flux
import org.cnds.orderservice.dtos.OrderDispatchMessage
import org.cnds.orderservice.dtos.OrderRequest
import org.cnds.orderservice.dtos.Product
import org.cnds.orderservice.product.ProductClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository, private val productClient: ProductClient) :
    OrderService {


    override fun getAllOrders() = orderRepository.findAll()

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
            .awaitSingle()

        return orderRepository.save(order)
    }

    override fun consumeOrderDispatchedEvent(it: Flow<OrderDispatchMessage>): Flow<Order> {
        return it
            .map { message -> orderRepository.findById(message.orderId.toInt())!! }
            .map { order -> buildDispatchOrder(order) }
            .map { order -> orderRepository.save(order) }
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