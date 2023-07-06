package refrigerator.back.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class RandomNumberConfig {

    @Bean
    public int intRandomNumber(){
        return new Random().nextInt(5);
    }

}
