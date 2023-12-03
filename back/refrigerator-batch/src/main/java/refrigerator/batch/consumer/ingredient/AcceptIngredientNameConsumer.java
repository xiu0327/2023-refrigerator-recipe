package refrigerator.batch.consumer.ingredient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import refrigerator.batch.trigger.AddIngredientJobLauncher;

@Component
@Slf4j
@RequiredArgsConstructor
public class AcceptIngredientNameConsumer {

    private final AddIngredientJobLauncher addIngredientJobLauncher;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void AcceptIngredientName(String name){
        addIngredientJobLauncher.addIngredientJobRun(name);
    }
}
