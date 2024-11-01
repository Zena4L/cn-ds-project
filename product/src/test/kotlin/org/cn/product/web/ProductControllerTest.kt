package org.cn.product.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.cn.product.domain.Product
import org.cn.product.domain.ProductServiceImpl
import org.cn.product.dtos.ProductRequest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import java.time.Instant

@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var productController: ProductController

    @MockBean
    private lateinit var productServiceImpl: ProductServiceImpl

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var request: ProductRequest

    private lateinit var createdProduct: Product

    @BeforeEach
    fun setup() {
        request = ProductRequest("Test Product", BigDecimal(20))
        createdProduct = Product(
            1,
            "Test Product",
            BigDecimal(20),
            Instant.now(),
            null,
            null
        )
    }

    @Test
    fun `when POST to product then should return 201`() {

        given(productServiceImpl.createProduct(request)).willReturn(createdProduct)

        mockMvc.post("/api/v1/product") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isCreated() }
                content { objectMapper.writeValueAsString(createdProduct) }
            }

    }

    @Test
    fun `when get all product then return paginated list of products`() {
        val page = 0
        val size = 10
        val listOfProd = listOf(createdProduct)
        val expectedProduct = PageImpl(
            listOfProd,
            PageRequest.of(page,size),
            listOfProd.size.toLong()
        )

        given(productServiceImpl.getAllProducts(page,size)).willReturn(expectedProduct)

        mockMvc.get("/api/v1/product"){
            param("page",page.toString())
            param("size", size.toString())
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { objectMapper.writeValueAsString(expectedProduct.content) }
        }
    }

    @Test
    fun `when give a product ID then return product`() {
        val productId = 1;
        given(productServiceImpl.getProduct(productId)).willReturn(createdProduct)

        mockMvc.get("/api/v1/product/$productId"){
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { objectMapper.writeValueAsString(createdProduct) }
        }
    }

}