package refrigerator.back.global.time;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ServiceCurrentTime implements CurrentTime {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
