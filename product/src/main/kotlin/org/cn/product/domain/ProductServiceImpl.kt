package org.cn.product.domain

import org.cn.product.dtos.ProductRequest
import org.cn.product.dtos.ProductUpdated
import org.cn.product.exceptions.NotSuchProductException
import org.cn.product.utils.ErrorMessageConstant.NOT_FOUND_MESSAGE
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun createProduct(request: ProductRequest): Product {
        val product: Product = Product(
            name = request.name,
            price = request.price
        )

        return productRepository.save(product)
    }

    override fun getAllProducts(page: Int, size: Int): Page<Product> {
        val pageRequest = PageRequest.of(page, size)
        return productRepository.findAll(pageRequest)
    }

    override fun getProduct(id: Int): Product {
        return productRepository.findById(id).orElseThrow { NotSuchProductException(NOT_FOUND_MESSAGE) }
    }

    @Transactional
    override fun updateProduct(id: Int, productRequest: ProductUpdated): Product {
        val existingProduct = productRepository.findById(id).orElseThrow { NotSuchProductException(NOT_FOUND_MESSAGE) }

        productRequest.name?.let { existingProduct.name = it }
        productRequest.price?.let { existingProduct.price = it }
        return existingProduct
    }

    override fun deleteProduct(id: Int) {
        productRepository.deleteById(id)
    }

}