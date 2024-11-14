package org.cnds.orderservice.domain

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.cnds.orderservice.dtos.OrderRequest
import org.cnds.orderservice.dtos.Product
import org.cnds.orderservice.product.ProductClient
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository, private val productClient: ProductClient) : OrderService {


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