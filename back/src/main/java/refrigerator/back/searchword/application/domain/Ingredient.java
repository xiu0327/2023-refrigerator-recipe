package refrigerator.back.searchword.application.domain;

import java.time.LocalDate;
import java.time.Period;

public class Ingredient {
    private String name;
    private LocalDate expirationDate;

    public String getName() {
        return name;
    }

    public Integer calculationDDay(){
        return Period.between(expirationDate, LocalDate.now()).getDays();
    }

    public boolean isExpired(){
        int days = Period.between(LocalDate.now(), expirationDate).getDays();
        return days <= 0;
    }
}
