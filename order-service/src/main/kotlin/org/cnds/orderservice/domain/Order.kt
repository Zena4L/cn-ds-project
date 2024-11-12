package org.cnds.orderservice.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener::class)
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    var id: Int? = null,

    @Column(nullable = false)
    var productName: String = "",

    @Column(nullable = false)
    var productPrice: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var quantity: Int = 0,

    @Column(nullable = false)
    var orderStatus: OrderStatus = OrderStatus.PENDING,

    @Column(nullable = false, updatable = false)
    @CreatedDate
    var createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null,

    @Version
    var version: Int? = null
) {
    constructor() : this(
        id = null,
        productName = " ",
        productPrice = BigDecimal.ZERO,
        quantity = 0,
        orderStatus = OrderStatus.PENDING,
        createdAt = null,
        updatedAt = null,
        version = null
    )
}

enum class OrderStatus {
    ACCEPTED,
    PENDING,
    REJECTED,
    DISPATCHED
}
