package org.cnds.orderservice.persistence

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(R2dbcProperties::class, FlywayProperties::class)
class DatabaseConfig {

    @Bean(initMethod = "migrate")
    fun flyway(flywayProperties: FlywayProperties, r2dbcProperties: R2dbcProperties): Flyway {
        println("Flyway URL: ${flywayProperties.url}")
        return Flyway.configure()
            .dataSource(
                flywayProperties.url,
                r2dbcProperties.username,
                r2dbcProperties.password
            )
            .locations(*flywayProperties.locations.toTypedArray())
            .baselineOnMigrate(true)
            .load()
    }
}