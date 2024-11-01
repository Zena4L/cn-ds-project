package org.cn.product.domain

import org.cn.product.dtos.ProductRequest
import org.cn.product.dtos.ProductUpdated
import org.springframework.data.domain.Page

interface ProductService {
    fun createProduct(request: ProductRequest): Product
    fun getAllProducts(page: Int, size: Int): Page<Product>
    fun getProduct(id: Int): Product
    fun updateProduct(id: Int, productRequest: ProductUpdated): Product
    fun deleteProduct(id: Int)
}