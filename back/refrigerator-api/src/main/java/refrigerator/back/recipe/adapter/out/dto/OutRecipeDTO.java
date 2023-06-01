package refrigerator.back.recipe.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;

@Getter
public class OutRecipeDTO {
    private Long recipeID;
    private String recipeName;
    private String image;
    private RecipeScore score;
    private Integer views;

    @QueryProjection
    public OutRecipeDTO(Long recipeID, String recipeName, String image, RecipeScore score, Integer views) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.image = image;
        this.score = score;
        this.views = views;
    }
}
