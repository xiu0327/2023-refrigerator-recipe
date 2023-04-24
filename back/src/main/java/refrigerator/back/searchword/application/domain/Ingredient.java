package refrigerator.back.searchword.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Builder
public class Ingredient {
    private String name;
    private LocalDate date;

    public Integer calculationDDay(){
        Period period = Period.between(LocalDate.now(), date);
        return period.getDays() + period.getMonths() * 30 + period.getYears() * 365;
    }

    public boolean isExpired(){
        int days = calculationDDay();
        return days >= 0;
    }
}
