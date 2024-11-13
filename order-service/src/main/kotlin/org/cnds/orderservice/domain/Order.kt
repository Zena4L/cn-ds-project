package org.cnds.orderservice.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table(name = "orders")
data class Order(
    @Id
    val id: Int? = null,

    val productName: String = "",

    val productId: Int = 0,

    val productPrice: Int = 0,

    val quantity: Int = 0,

    val orderStatus: OrderStatus = OrderStatus.PENDING,

    @CreatedDate
    val createdAt: Instant? = null,

    @LastModifiedDate
    val updatedAt: Instant? = null,

    @Version
    val version: Int? = null
) {
    companion object {
        fun of(
            productName: String,
            productId: Int,
            productPrice: Int,
            quantity: Int,
            orderStatus: OrderStatus
        ): Order {
            return Order(
                id = null,
                productName = productName,
                productId = productId,
                productPrice = productPrice,
                quantity = quantity,
                orderStatus = orderStatus,
                createdAt = null,
                updatedAt = null,
                version = null
            )
        }
    }
}


enum class OrderStatus {
    ACCEPTED,
    PENDING,
    REJECTED,
    DISPATCHED
}
