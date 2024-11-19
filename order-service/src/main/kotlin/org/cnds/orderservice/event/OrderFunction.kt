package org.cnds.orderservice.event

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.cnds.orderservice.domain.OrderService
import org.cnds.orderservice.dtos.OrderDispatchMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class OrderFunction {

    companion object {
        @JvmStatic
        val logger: Logger = LoggerFactory.getLogger(OrderFunction::class.java)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Bean
    fun dispatchOrder(orderService: OrderService): Consumer<Flow<OrderDispatchMessage>> {
        return Consumer { flow ->
            flow
                .let { orderService.consumeOrderDispatchedEvent(it) }
                .onEach { logger.info("The order with id $it is dispatched") }
                .launchIn(GlobalScope)
        }
    }

}