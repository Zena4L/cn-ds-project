package org.cnds.orderservice.domain

import kotlinx.coroutines.flow.Flow

interface OrderService {

    fun getProduct(): Flow<Order>
    suspend fun submitOrder(productId: Int, quantity: Int): Order?

}