package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.application.dto.RecipeDto;

public interface FindRecipeDetailsUseCase {
    RecipeDto findRecipeDetails(Long recipeId, String memberId, boolean isViewed);
}
