package org.cn.product.integration

import org.assertj.core.api.Assertions.assertThat
import org.cn.product.domain.Product
import org.cn.product.domain.ProductRepository
import org.cn.product.persistence.DataConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import kotlin.test.assertTrue


@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataConfig::class)
class DataPersistenceIntegrationTest @Autowired constructor(
    private val productRepository: ProductRepository,
    private val entityManager: TestEntityManager
) {

    private lateinit var createdProduct: Product

    companion object {
        @Container
        @ServiceConnection
        val container = PostgreSQLContainer("postgres:latest")
    }

    @BeforeEach
    fun setup() {
        createdProduct = Product(
            name = "Test product",
            price = BigDecimal(20),
        )

        productRepository.save(createdProduct)
    }

    @Test
    fun `container is up and running`() {
        assertTrue(container.isCreated)
        assertTrue(container.isRunning)
    }


    @Test
    fun `when given an ID return with Product is it exist`() {
        val actualProduct = productRepository.findById(1)

        assertThat(actualProduct).isPresent
        assertThat(actualProduct.get()).usingRecursiveComparison().isEqualTo(createdProduct)
    }

}