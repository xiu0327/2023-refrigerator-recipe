package refrigerator.back.ingredient.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
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

    public Long getRemainDays() {
        return ChronoUnit.DAYS.between(this.expirationDate, LocalDate.now());
    }
}
