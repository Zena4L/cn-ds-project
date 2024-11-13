package org.cnds.orderservice.domain

import org.cnds.orderservice.dtos.OrderRequest
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository) : OrderService {


    override fun getAllOrders() = orderRepository.findAll()

    override suspend fun submitOrder(orderRequest: OrderRequest): Order {
        val order = buildRejectOrder(orderRequest.productId, orderRequest.quantity, orderRequest.productName, orderRequest.productPrice)
        return orderRepository.save(order)  // This will handle the insertion correctly
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

}