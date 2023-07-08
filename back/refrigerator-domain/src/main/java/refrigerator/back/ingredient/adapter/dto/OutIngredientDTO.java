package refrigerator.back.ingredient.adapter.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
public class OutIngredientDTO {

    private Long ingredientID;
    private String name;
    private LocalDate expirationDate;
    private Integer remainDays;
    private String image;


    @QueryProjection
    public OutIngredientDTO(Long ingredientID, String name, LocalDate expirationDate, Integer remainDays, String image) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.expirationDate = expirationDate;
        this.remainDays = remainDays;
        this.image = image;
    }
}
