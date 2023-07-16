package refrigerator.back.ingredient.adapter.mapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import refrigerator.back.notification.adapter.mapper.OutNotificationMapper;
import refrigerator.back.notification.adapter.mapper.OutNotificationMapperImpl;

@TestConfiguration
public class OutIngredientMapperConfig {

    @Bean
    public OutIngredientMapper outIngredientMapper(){
        return new OutIngredientMapperImpl();
    }
}
