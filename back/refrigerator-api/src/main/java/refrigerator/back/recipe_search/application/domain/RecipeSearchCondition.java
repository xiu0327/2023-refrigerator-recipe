package refrigerator.back.recipe_search.application.domain;

import lombok.Builder;
import lombok.Getter;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.recipe.exception.RecipeExceptionType;

@Getter
@Builder
public class RecipeSearchCondition {
    private String searchWord;
    private String recipeType;
    private String recipeFoodType;
    private String difficulty;
    private String category;
    private Double score;

    public void parameterCheck() {
        if (score != null){
            if (score < 0.0 || score > 5.0){
                throw new BusinessException(RecipeExceptionType.NOT_ACCEPTABLE_RANGE);
            }
        }
    }
}
