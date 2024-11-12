package org.cnds.orderservice.domain

import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderServiceImpl(
    @Autowired val orderRepository: OrderRepository
) : OrderService {

    override fun getProduct(): Flow<Order> {
        return orderRepository.findAll()
    }

    override suspend fun submitOrder(productId: Int, quantity: Int): Order? {
        val order = buildRejectOrder(productId, quantity)
        return orderRepository.save(order)
    }

    companion object {
        fun buildRejectOrder(productId: Int, quantity: Int): Order {
            return Order(id = productId, quantity = quantity, orderStatus = OrderStatus.REJECTED)
        }
    }

}