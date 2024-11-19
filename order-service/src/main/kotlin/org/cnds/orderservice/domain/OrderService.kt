package org.cnds.orderservice.domain

import kotlinx.coroutines.flow.Flow
import org.cnds.orderservice.dtos.OrderDispatchMessage
import org.cnds.orderservice.dtos.OrderRequest

interface OrderService {

    fun getAllOrders() : Flow<Order>
    suspend fun submitOrder(orderRequest: OrderRequest): Order
    fun consumeOrderDispatchedEvent(it: Flow<OrderDispatchMessage>) : Flow<Order>
}