package refrigerator.back.notification.adapter.mapper;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OutNotificationMapperConfig {

    @Bean
    public OutNotificationMapper outNotificationMapper(){
        return new OutNotificationMapperImpl();
    }
}
