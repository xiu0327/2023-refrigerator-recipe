package refrigerator.back.recipe_searchword.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
public class Ingredient {
    private String name;
    private LocalDate date;

    // TODO : 여기 LocalDate.now() 수정함.. mapper에서 사용. 하지만 실제로 사용되지 않음. 일단 외부에서 값을 주입받도록 바꿈, 지워도 된다면 지우길..
    
    public boolean isExpired(LocalDate now){
        long between = ChronoUnit.DAYS.between(this.date, now);

        return between >= 0;
    }
}
