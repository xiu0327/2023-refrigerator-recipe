package refrigerator.back.ingredient.application.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecipeIngredientDto {
    private Long ingredientId;
    private String name;
}
