package refrigerator.back.recipe.application.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeSearchCondition {
    private String searchWord;
    private String recipeType;
    private String recipeFoodType;
    private String difficulty;
    private Double score;

    public void parameterCheck() {
    }
}
