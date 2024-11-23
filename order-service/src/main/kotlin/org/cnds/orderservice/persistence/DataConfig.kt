package org.cnds.orderservice.persistence

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext
import org.springframework.security.core.context.ReactiveSecurityContextHolder

@Configuration
@EnableR2dbcAuditing
class DataConfig {
    @Bean
    fun r2dbcMappingContext(): R2dbcMappingContext {
        return R2dbcMappingContext()
    }

    @Bean
    fun auditorAware() {
        ReactiveAuditorAware {
            ReactiveSecurityContextHolder.getContext()
                .map { it.authentication }
                .filter { it.isAuthenticated }
                .map { it.name }
        }
    }


}