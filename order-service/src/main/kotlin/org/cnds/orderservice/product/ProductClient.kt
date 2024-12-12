package org.cnds.orderservice.product

import org.cnds.orderservice.dtos.Product
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

@Component
class ProductClient(private val webClient: WebClient) {

    companion object {
        private const val PRODUCT_ROOT_API = "/product/{id}"
    }

    fun getProduct(id: Int): Mono<Product> =
        webClient.get()
            .uri { builder -> builder.path(PRODUCT_ROOT_API).build(id) }
            .retrieve()
            .bodyToMono(Product::class.java)
            .timeout(Duration.ofSeconds(3), Mono.empty())
            .onErrorResume { throwable ->
                when (throwable) {
                    is WebClientResponseException.NotFound -> Mono.empty()
                    else -> Mono.error(throwable)
                }
            }
            .retryWhen(
                Retry.backoff(3, Duration.ofSeconds(100))
            )
            .onErrorResume { throwable ->
                when (throwable) {
                    is Exception -> Mono.empty()
                    else -> Mono.error(throwable)
                }
            }

}