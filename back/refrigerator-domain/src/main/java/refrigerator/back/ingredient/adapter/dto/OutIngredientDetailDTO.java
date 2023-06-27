package refrigerator.back.ingredient.adapter.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import refrigerator.back.global.time.ServiceDate;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@RequiredArgsConstructor
public class OutIngredientDetailDTO {

    private Long ingredientID;
    private String name;
    private IngredientStorageType storage;
    private LocalDate expirationDate;
    private LocalDate registrationDate;
    private Double volume;
    private String unit;

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

    // TODO : LocalData.now 빼기

    public String getRemainDays() {
        long between = ChronoUnit.DAYS.between(this.expirationDate, LocalDate.now());

        if(between > 0) {
            return "+" + Long.toString(between);
        } else {
            return Long.toString(between);
        }
    }
}
