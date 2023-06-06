package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class OutRecipeIngredientNameDTO {
    private Long recipeId;
    private String ingredientName;
    private String type;

    @QueryProjection
    public OutRecipeIngredientNameDTO(Long recipeId, String ingredientName) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
    }

    @QueryProjection
    public OutRecipeIngredientNameDTO(Long recipeId, String ingredientName, String type) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.type = type;
    }
}
