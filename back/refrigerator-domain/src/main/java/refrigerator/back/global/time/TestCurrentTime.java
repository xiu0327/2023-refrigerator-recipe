package refrigerator.back.global.time;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TestCurrentTime implements CurrentTime{

    private final LocalDateTime time;

    @Override
    public LocalDateTime now() {
        return this.time;
    }

    public static TestCurrentTime of(int year, int month, int day, int hour, int minute, int second) {
        return new TestCurrentTime(LocalDateTime.of(year, month, day, hour, minute, second));
    }
}
