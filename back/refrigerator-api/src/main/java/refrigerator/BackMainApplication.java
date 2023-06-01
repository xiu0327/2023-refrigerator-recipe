package refrigerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
@EnableAspectJAutoProxy
@Slf4j
public class BackMainApplication {

    @PostConstruct
    public void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("LocalDate 현재시각 : {}", LocalDateTime.now());
        log.info("Date 현재시각: {}", new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(BackMainApplication.class, args);
    }

}