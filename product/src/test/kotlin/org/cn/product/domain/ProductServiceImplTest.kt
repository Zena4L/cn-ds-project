package org.cn.product.domain

import org.cn.product.dtos.ProductRequest
import org.cn.product.dtos.ProductUpdated
import org.cn.product.exceptions.NotSuchProductException
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.time.Instant
import java.util.*


class ProductServiceImplTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var productServiceImpl: ProductServiceImpl
    private lateinit var p1: Product

    @BeforeEach
    fun setUp() {
        productRepository = mock(ProductRepository::class.java)
        productServiceImpl = ProductServiceImpl(productRepository)

        p1 = Product(
            name = "Test Product",
            price = BigDecimal(10.00)
        )

    }

    @Test
    fun `when give product request the return new product `() {
        //given
        val productRequest: ProductRequest = ProductRequest(
            name = "Test Product",
            BigDecimal(20.0)
        )

        //when
        `when`(productRepository.save(any(Product::class.java))).thenReturn(p1)
        val res = productServiceImpl.createProduct(productRequest)

        assertNotNull(res)
        assertEquals(p1.name, res.name)
        assertEquals(p1.price, res.price)
        verify(productRepository).save(any(Product::class.java))
    }

    @Test
    fun `when given an id then return product`() {
        val productId = 1
        val expectedProduct: Product = Product(
            productId,
            "Test product",
            BigDecimal(200),
            Instant.now(),
            null,
            null
        )

        //when
        `when`(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct))
        val res = productServiceImpl.getProduct(productId)

        assertNotNull(res)
        assertEquals(expectedProduct.name, res.name)
        assertEquals(expectedProduct.price, res.price)
    }

    @Test
    fun `given invalid product Id, throws not found exception`() {
        val productId = 999
        `when`(productRepository.findById(productId)).thenReturn(Optional.empty())

        val exception = assertThrows<NotSuchProductException> { productServiceImpl.getProduct(productId) }

        assertNotNull(exception)
    }

    @Test
    fun `retrieve pages of products`() {
        val page = 0
        val size = 5
        val pageRequest = PageRequest.of(page, size)
        val expectedPages = PageImpl(
            listOf(p1), pageRequest,size.toLong()
        )

        `when`(productRepository.findAll(pageRequest)).thenReturn(expectedPages)

        val res = productServiceImpl.getAllProducts(page,size)

        assertEquals(1,res.content.size)
        assertEquals(expectedPages.content, res.content)
        verify(productRepository, times(1)).findAll(pageRequest)
    }

    @Test
    fun `when given an ID, then update product`(){
        val existingProduct = Product(id = 1, name = "Old Name", price = BigDecimal("10.00"))
        val updatedProductRequest = ProductUpdated(name = "New Name", price = BigDecimal("20.00"))

        `when`(productRepository.findById(1)).thenReturn(Optional.of(existingProduct))

        // Act
        val updatedProduct = productServiceImpl.updateProduct(1, updatedProductRequest)

        // Assert
        assertEquals("New Name", updatedProduct.name)
        assertEquals(BigDecimal("20.00"), updatedProduct.price)
    }



}