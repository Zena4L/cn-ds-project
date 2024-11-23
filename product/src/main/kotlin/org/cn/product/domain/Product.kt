package org.cn.product.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_sequence", allocationSize = 1)
    var id: Int? = null,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var price: BigDecimal = BigDecimal.ZERO,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: Instant? = null,

    @LastModifiedDate
    var updatedAt: Instant? = null,

    @CreatedBy
    var createdBy: String? = null,

    @LastModifiedBy
    var lastModifiedBy: String? = null,

    @Version
    var version: Int? = null
) {
    constructor() : this(id = null, name = "", price = BigDecimal.ZERO)
}
