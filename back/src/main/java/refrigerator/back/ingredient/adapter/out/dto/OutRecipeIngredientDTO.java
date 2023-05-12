package refrigerator.back.ingredient.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class OutRecipeIngredientDTO {
    private Long ingredientId;
    private String name;

    @QueryProjection
    public OutRecipeIngredientDTO(Long ingredientId, String name) {
        this.ingredientId = ingredientId;
        this.name = name;
    }
}
