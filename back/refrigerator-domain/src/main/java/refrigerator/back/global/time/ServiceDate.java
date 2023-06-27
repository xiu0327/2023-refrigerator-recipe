package refrigerator.back.global.time;

import java.time.LocalDate;

public class ServiceDate implements Date{
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
