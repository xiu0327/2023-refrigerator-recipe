package refrigerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@PropertySource("classpath:application.yml")
@Slf4j
public class ServerApplication {

    @PostConstruct
    public void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("LocalDate 현재시각 : {}", LocalDateTime.now());
        log.info("Date 현재시각: {}", new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
