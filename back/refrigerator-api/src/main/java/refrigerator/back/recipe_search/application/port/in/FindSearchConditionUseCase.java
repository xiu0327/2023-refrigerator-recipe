package refrigerator.back.recipe_search.application.port.in;

import java.util.List;

public interface FindSearchConditionUseCase {
    List<String> findRecipeFoodTypeCond();
    List<String> findRecipeCategoryCond();
    List<String> findRecipeTypeCond();
    List<String> findRecipeDifficultyCond();
}
