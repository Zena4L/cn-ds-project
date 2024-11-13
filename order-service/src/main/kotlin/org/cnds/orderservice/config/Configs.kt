package org.cnds.orderservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI

@ConfigurationProperties(prefix = "client")
data class ClientProperties(val productServiceUri: URI)

@Configuration
class ClientConfig() {
    @Bean
    fun webClient(clientProperties: ClientProperties, webClientBuilder: WebClient.Builder): WebClient {
        return webClientBuilder.baseUrl(clientProperties.productServiceUri.toString()).build();
    }

}
