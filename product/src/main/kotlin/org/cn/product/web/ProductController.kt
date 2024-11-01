package org.cn.product.web

import jakarta.validation.Valid
import org.cn.product.domain.Product
import org.cn.product.domain.ProductService
import org.cn.product.dtos.ProductRequest
import org.cn.product.dtos.ProductUpdated
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val productService: ProductService) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody @Valid request: ProductRequest): Product {
        return productService.createProduct(request)
    }

    @GetMapping
    fun getAllProduct(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<Product> {
        return productService.getAllProducts(page, size)

    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable("id") id: Int) = productService.getProduct(id)

    @PatchMapping("/{id}")
    fun updateProduct(@PathVariable("id") id: Int, @RequestBody productRequest: ProductUpdated) =
        productService.updateProduct(id, productRequest);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable("id") id: Int) {
        productService.deleteProduct(id)
    }
}