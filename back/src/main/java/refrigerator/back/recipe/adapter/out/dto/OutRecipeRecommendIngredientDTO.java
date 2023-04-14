package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class OutRecipeRecommendIngredientDTO {
    private Long recipeId;
    private String ingredientName;

    @QueryProjection
    public OutRecipeRecommendIngredientDTO(Long recipeId, String ingredientName) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
    }
}
