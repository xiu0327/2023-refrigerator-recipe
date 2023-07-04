package refrigerator.back.global.time;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ServiceCurrentDate implements CurrentDate {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
