package org.cn.product.demo

import org.cn.product.domain.Product
import org.cn.product.domain.ProductRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.unleash.features.annotation.Toggle
import java.math.BigDecimal

interface ProductData {
    @Toggle(name = "ProductDataFlag", alterBean = "productDataTestLoader")
    fun loadData()
}

@Component("productDataTestLoader")
class ProductDataTestLoader(private val productRepository: ProductRepository) : ProductData {
    override fun loadData() {
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

@Component("productDataDevLoader")
class ProductLoadDevLoader(private val productRepository: ProductRepository) : ProductData {
    override fun loadData() {
        val product1 = Product(
            name = "Test Product 1",
            price = BigDecimal(100)
        )
        val product2 = Product(
            name = "Test Product 2",
            price = BigDecimal(500)
        )

        productRepository.saveAll(listOf(product1, product2))
    }
}