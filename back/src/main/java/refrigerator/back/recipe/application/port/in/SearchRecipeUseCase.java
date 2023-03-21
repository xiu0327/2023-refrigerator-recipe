package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.application.domain.entity.RecipeDomain;

import java.util.List;

public interface SearchRecipeUseCase {
    List<RecipeDomain> search(String recipeType, String foodType,
                              String difficulty, Double score,
                              int page, int size);
}
