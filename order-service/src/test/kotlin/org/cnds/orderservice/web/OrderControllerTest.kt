package org.cnds.orderservice.web

import kotlinx.coroutines.runBlocking
import org.cnds.orderservice.domain.Order
import org.cnds.orderservice.domain.OrderService
import org.cnds.orderservice.domain.OrderServiceImpl
import org.cnds.orderservice.domain.OrderStatus
import org.cnds.orderservice.dtos.OrderRequest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient


@WebFluxTest(controllers = [OrderController::class])
class OrderControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient
    @MockBean
    lateinit var orderService: OrderServiceImpl

    @Test
    fun submitOrder() {
    runBlocking {
        val orderRequest = OrderRequest(
            productId = 99,
            productName = "Test product",
            quantity = 99,
            productPrice = 100
        )

        val expectedResponse = Order(
            productId = 99,
            productName = "Test product",
            quantity = 99,
            productPrice = 100,
            orderStatus = OrderStatus.REJECTED,
        )
        given(orderService.submitOrder(orderRequest)).willReturn(expectedResponse)

        webTestClient.post().uri("/api/v1/order")
            .bodyValue(orderRequest)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(Order::class.java).value {
                assertNotNull(it.orderStatus)
                assertEquals(OrderStatus.REJECTED, it.orderStatus)
            }
    }
    }
}