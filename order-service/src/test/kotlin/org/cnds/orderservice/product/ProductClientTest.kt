package org.cnds.orderservice.product


import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.testcontainers.shaded.com.google.common.net.HttpHeaders
import reactor.test.StepVerifier
import kotlin.jvm.Throws


class ProductClientTest() {

    private lateinit var productClient: ProductClient
    private lateinit var mockWebServer: MockWebServer


    @BeforeEach
    @Throws(IOException::class)
    fun setup() {
        mockWebServer = MockWebServer()
        val webClient = WebClient.builder()
            .baseUrl(mockWebServer.url("/api/v1").toString()).build()

        productClient = ProductClient(webClient)
    }

    @AfterEach
    fun cleanUp() {
        mockWebServer.shutdown()
    }

    @Test
    fun `when product exist then return product`() {
        val productId = 1

        val mockResponse = MockResponse()
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .setBody(
                """
                { "id": 1, "name": "Product 1", "price": 200 }
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        val product = productClient.getProduct(productId)

        StepVerifier.create(product)
            .expectNextMatches {
                it ->
             it.id == productId && it.name == "Product 1"
            }
            .verifyComplete()

    }

    @Test
    fun `when product does not exist return empty`(){
       val productId = 999

        val mockResponse = MockResponse()
            .setResponseCode(HttpStatus.NOT_FOUND.value())
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        mockWebServer.enqueue(mockResponse)

        val product = productClient.getProduct(productId)

        StepVerifier.create(product)
            .expectNextCount(0)
            .verifyComplete()
    }

}