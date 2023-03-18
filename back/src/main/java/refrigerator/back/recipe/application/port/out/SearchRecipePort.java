package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.entity.RecipeDomain;

import java.util.List;

public interface SearchRecipePort {
    List<RecipeDomain> search(String recipeType, String foodType, String difficulty, Double score,
                              int page, int size);
    List<String> findRecipeFoodTypeCond();
    List<String> findRecipeCategoryCond();
}
