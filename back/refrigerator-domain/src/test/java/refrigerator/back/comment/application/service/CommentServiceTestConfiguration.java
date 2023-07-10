package refrigerator.back.comment.application.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.global.time.TimeService;

import java.time.LocalDateTime;

@TestConfiguration
@ComponentScan(basePackages = "refrigerator.back.comment")
public class CommentServiceTestConfiguration {

    @Bean
    public CommentTimeService commentTimeService(){
        return new TimeService(currentTime());
    }

    @Bean
    public CurrentTime<LocalDateTime> currentTime(){
        return () -> LocalDateTime.of(2023, 7, 20, 6, 48);
    }
}
