package org.cnds.deliveryservice.domain;

import org.cnds.deliveryservice.dto.OrderAcceptMessage;
import org.cnds.deliveryservice.dto.OrderDispatchedMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;


@FunctionalSpringBootTest
class DispatchFunctionsTest {


    @Autowired
    private FunctionCatalog functionCatalog;

    @Test
    void packAndLabelOrder() {
        Function<OrderAcceptMessage, Flux<OrderDispatchedMessage>> packAndLabel =
                functionCatalog.lookup(
                        Function.class,
                        "pack|label"
                );

        Long orderId = 1L;

        StepVerifier.create(packAndLabel.apply(new OrderAcceptMessage(orderId)))
                .expectNextMatches(
                        dispatchOrder ->
                                dispatchOrder.equals(new OrderDispatchedMessage(orderId))
                ).verifyComplete();
    }

}