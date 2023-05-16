package refrigerator.back.ingredient.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
public class OutIngredientDetailDTO {

    private Long ingredientID;
    private String name;
    private LocalDate expirationDate;
    private LocalDate registrationDate;
    private Double volume;
    private String unit;
    private IngredientStorageType storage;
    private String image;

    @QueryProjection
    public OutIngredientDetailDTO(Long ingredientID, String name, LocalDate expirationDate, LocalDate registrationDate, Double volume, String unit, IngredientStorageType storage, String image) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.expirationDate = expirationDate;
        this.registrationDate = registrationDate;
        this.volume = volume;
        this.unit = unit;
        this.storage = storage;
        this.image = image;
    }

    public String getRemainDays() {
        long between = ChronoUnit.DAYS.between(this.expirationDate, LocalDate.now());

        if(between > 0) {
            return "+" + Long.toString(between);
        } else {
            return Long.toString(between);
        }
    }
}
