package org.cn.product.demo

import org.cn.product.domain.Product
import org.cn.product.domain.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
@Profile("test-data")
class ProductDataLoader @Autowired constructor(private val productRepository: ProductRepository) {

    @EventListener(ApplicationReadyEvent::class)
    fun loadTestProductData() {
        val product1 = Product(
            name = "Iphone",
            price = BigDecimal(100)
        )
        val product2 = Product(
            name = "MacBook",
            price = BigDecimal(500)
        )

        productRepository.saveAll(listOf(product1, product2))
    }
}