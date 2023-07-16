package refrigerator.back.ingredient.adapter.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@ToString
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
}
