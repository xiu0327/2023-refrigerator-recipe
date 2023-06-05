package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;


@Getter
public class OutRecipeRecommendDTO {
    private Long recipeId;
    private String recipeName;
    private String recipeImage;
    private RecipeScore score;

    @QueryProjection
    public OutRecipeRecommendDTO(Long recipeId, String recipeName, String recipeImage, RecipeScore recipeScore) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
        this.score = recipeScore;
    }
}
