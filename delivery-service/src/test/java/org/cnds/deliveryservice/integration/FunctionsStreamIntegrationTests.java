package org.cnds.deliveryservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cnds.deliveryservice.dto.OrderAcceptMessage;
import org.cnds.deliveryservice.dto.OrderDispatchedMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class FunctionsStreamIntegrationTests {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenOrderAcceptedThenDispatch() throws Exception {
        long orderId = 121;

        Message<OrderAcceptMessage> inputMessage = MessageBuilder.withPayload(new OrderAcceptMessage(orderId)).build();

        Message<OrderDispatchedMessage> expecedMessage = MessageBuilder.withPayload(new OrderDispatchedMessage(orderId)).build();

        this.input.send(inputMessage);

        assertThat(objectMapper.readValue(output.receive().getPayload(), OrderDispatchedMessage.class)).isEqualTo(expecedMessage.getPayload());

    }
}
