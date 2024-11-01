package org.cn.product.integration

import org.assertj.core.api.Assertions.assertThat
import org.cn.product.domain.Product
import org.cn.product.domain.ProductServiceImpl
import org.cn.product.dtos.ProductRequest
import org.cn.product.exceptions.NotSuchProductException
import org.cn.product.utils.ErrorMessageConstant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.math.BigDecimal
import java.time.Instant

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebIntegrationTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var productServiceImpl: ProductServiceImpl

    private lateinit var createdProduct: Product

    @BeforeEach
    fun setup(){
        createdProduct = Product(
            id = 1,
            name = "test product",
            price = BigDecimal(20),
            createdAt = Instant.now(),
            updatedAt = null,
            version = null
        )


    }

    @Test
    fun `when post request with valid product then product is created`() {
        val productRequest = ProductRequest("test product", BigDecimal(20))

        given(productServiceImpl.createProduct(productRequest)).willReturn(createdProduct)

        webTestClient.post().uri("/api/v1/product")
            .contentType(MediaType.APPLICATION_JSON).bodyValue(productRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Product::class.java).value {
                actualProduct -> assertThat(actualProduct).isNotNull()
                assertThat(actualProduct.name).isEqualTo(createdProduct.name)
            }
    }

    @Test
    fun `given a product ID, return product if found `(){
       val productId = 1

        given(productServiceImpl.getProduct(productId)).willReturn(createdProduct)

        webTestClient.get().uri("/api/v1/product/$productId")
            .exchange()
            .expectStatus().isOk
            .expectBody(Product::class.java).value {
                actualProduct -> assertThat(actualProduct).isNotNull
                assertThat(actualProduct.name).isEqualTo(createdProduct.name)
            }
    }

    @Test
    fun `given a invalid ID should return 404 not found`(){
       val productId = 999

        given(productServiceImpl.getProduct(productId)).willThrow(NotSuchProductException(ErrorMessageConstant.NOT_FOUND_MESSAGE))

        webTestClient.get().uri("/api/v1/product/$productId")
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `when GET request for all product then return paginated products`(){
        val page = 0;
        val size = 10;
       val listOfProduct = listOf(createdProduct)

        val paginatedPages = PageImpl(listOfProduct, PageRequest.of(page,size), listOfProduct.size.toLong())

        given(productServiceImpl.getAllProducts(page,size)).willReturn(paginatedPages)

        webTestClient.get().uri {
            uriBuilder -> uriBuilder.path("/api/v1/product")
            .queryParam("page", page)
            .queryParam("size", size)
            .build();
        }.exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.content[0].name").isEqualTo(createdProduct.name)
    }
}