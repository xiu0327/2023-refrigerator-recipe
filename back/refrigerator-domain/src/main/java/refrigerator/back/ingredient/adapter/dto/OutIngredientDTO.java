package refrigerator.back.ingredient.adapter.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.global.time.CurrentTime;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
public class OutIngredientDTO {

    private Long ingredientID;
    private String name;
    private LocalDate expirationDate;
    private String image;

    @QueryProjection
    public OutIngredientDTO(Long ingredientID, String name, LocalDate expirationDate, String image) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.expirationDate = expirationDate;
        this.image = image;
    }

    public String getRemainDays(LocalDate now) {
        long between = ChronoUnit.DAYS.between(this.expirationDate, now);
        if(between > 0) {
            return "+" + between;
        } else {
            return Long.toString(between);
        }
    }
}
