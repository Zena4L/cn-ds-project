package org.cn.product.persistence

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
class DataConfig {

    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuditorAware {
            Optional.ofNullable(SecurityContextHolder.getContext())
                .map { it.authentication }
                .filter { it.isAuthenticated }
                .map { it.name }
        }
    }
}