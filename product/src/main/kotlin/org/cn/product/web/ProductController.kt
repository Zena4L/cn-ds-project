package org.cn.product.web

import jakarta.validation.Valid
import org.cn.product.domain.Product
import org.cn.product.domain.ProductService
import org.cn.product.dtos.ProductRequest
import org.cn.product.dtos.ProductUpdated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val productService: ProductService) {

    companion object {
        @JvmStatic
        val logger: Logger = LoggerFactory.getLogger(ProductController::class.java)
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody @Valid request: ProductRequest) = productService.createProduct(request)

    @GetMapping
    fun getAllProduct(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CollectionModel<EntityModel<Product>> {
        logger.info("Fetching all products")
        val products = productService.getAllProducts(page, size)

        val productModel = products.map { product ->
            EntityModel.of(
                product,
                linkTo(methodOn(ProductController::class.java).getProduct(product.id!!)).withSelfRel(),
                linkTo(methodOn(ProductController::class.java).getAllProduct(page, size)).withRel("products"),
                linkTo(methodOn(ProductController::class.java).deleteProduct(product.id!!)).withRel("delete"),
                linkTo(
                    methodOn(ProductController::class.java).updateProduct(
                        product.id!!,
                        ProductUpdated()
                    )
                ).withRel("update")

            )
        }.toList()

        val nextLink: Link = if (products.hasNext()) {
            linkTo(methodOn(ProductController::class.java).getAllProduct(page + 1, size)).withRel("next")
        } else {
            Link.of("/" + products.totalPages);
        }

        val previousLink: Link = if (products.hasPrevious()) {
            linkTo(methodOn(ProductController::class.java).getAllProduct(page - 1, size)).withRel("previous")
        } else {
            Link.of("/" + products.totalPages);
        }

        return PagedModel.of(
            productModel,
            PagedModel.PageMetadata(size.toLong(), page.toLong(), products.totalElements),
            nextLink,
            previousLink
        )
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable("id") id: Int): EntityModel<Product> {

        logger.info("Fetching product with id : $id")
        return EntityModel.of(
            productService.getProduct(id),
            linkTo(methodOn(ProductController::class.java).getProduct(id)).withSelfRel(),
            linkTo(methodOn(ProductController::class.java).getAllProduct(0, 10)).withRel("products"),
            linkTo(methodOn(ProductController::class.java).deleteProduct(id)).withRel("delete"),
            linkTo(methodOn(ProductController::class.java).updateProduct(id, ProductUpdated())).withRel("update")
        )
    }

    @PatchMapping("/{id}")
    fun updateProduct(@PathVariable("id") id: Int, @RequestBody productRequest: ProductUpdated) = productService.updateProduct(id, productRequest)



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable("id") id: Int) {
        logger.info("Deleting product with id : $id")
        productService.deleteProduct(id)
    }
}