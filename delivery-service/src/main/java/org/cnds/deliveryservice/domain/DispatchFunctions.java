package org.cnds.deliveryservice.domain;

import org.cnds.deliveryservice.dto.OrderAcceptMessage;
import org.cnds.deliveryservice.dto.OrderDispatchedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class DispatchFunctions {
    private static final Logger logger = LoggerFactory.getLogger(DispatchFunctions.class);

    @Bean
    public Function<OrderAcceptMessage, Long> pack() {
        return orderAcceptMessage -> {
            logger.info("The order with Id {} is packed", orderAcceptMessage.orderId());
            return orderAcceptMessage.orderId();
        };
    }


    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
        return orderFlux -> orderFlux.map(
                orderId -> {
                    logger.info("The order with Id {} is labeled", orderId);
                    return new OrderDispatchedMessage(orderId);
                }
        );
    }
}
