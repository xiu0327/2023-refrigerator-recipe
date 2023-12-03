package producer.ingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.application.port.admin.SendIngredientNamePort;

@RequiredArgsConstructor
@Component
public class SendIngredientNameProducer implements SendIngredientNamePort {

    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.bindkey}")
    private String bindKey;

    @Override
    public void sendIngredientName(String name) {
        amqpTemplate.convertAndSend(exchange, bindKey, name);
    }
}
