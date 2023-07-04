package refrigerator.back.global.time;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class TestCurrentDate implements CurrentDate {

    private final LocalDate date;

    @Override
    public LocalDate now() {
        return this.date;
    }

    public static TestCurrentDate of(int year, int month, int day) {
        return new TestCurrentDate(LocalDate.of(year, month, day));
    }
}
