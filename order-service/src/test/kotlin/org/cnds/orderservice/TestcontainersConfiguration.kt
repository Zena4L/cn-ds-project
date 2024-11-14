package org.cnds.orderservice

import kotlinx.coroutines.runBlocking
import org.cnds.orderservice.domain.Order
import org.cnds.orderservice.domain.OrderRepository
import org.cnds.orderservice.domain.OrderStatus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.test.assertTrue

@DataR2dbcTest
@TestConfiguration(proxyBeanMethods = false)
@Testcontainers
class TestcontainersConfiguration(
    @Autowired val orderRepository: OrderRepository
) {

    companion object {
        @Container
        @ServiceConnection
        val container = PostgreSQLContainer("postgres:latest")
    }

    @Test
    fun `container is up and running`() {
        assertTrue(container.isCreated)
        assertTrue(container.isRunning)
    }


    @Test
    fun `create Reject Order`() {
        runBlocking {

            val rejectOrder = Order(
                productName = "Test product",
                productId = 999,
                orderStatus = OrderStatus.REJECTED,
                productPrice = 100,
                quantity = 100
            )

            val product = orderRepository.save(rejectOrder)

            StepVerifier.create(Mono.just(product))
                .expectNextMatches{it.orderStatus == OrderStatus.REJECTED}
                .verifyComplete()
        }
    }

}
