package org.cnds.orderservice.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache

@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange {
            it.anyExchange().authenticated()
        }
        http.oauth2ResourceServer { it.jwt { } }
        http.requestCache { it.requestCache(NoOpServerRequestCache.getInstance()) }
        http.csrf { it.disable() }

        return http.build()
    }

}