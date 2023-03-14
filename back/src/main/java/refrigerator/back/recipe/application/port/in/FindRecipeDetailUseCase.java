package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.application.domain.entity.RecipeDomain;

public interface FindRecipeDetailUseCase {
    RecipeDomain getRecipe(Long recipeID, boolean isViewed);
}
