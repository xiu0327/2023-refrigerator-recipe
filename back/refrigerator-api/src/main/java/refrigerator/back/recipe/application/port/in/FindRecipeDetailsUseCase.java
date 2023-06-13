package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.application.domain.dto.RecipeDto;

public interface FindRecipeDetailsUseCase {
    RecipeDto findRecipeDetails(Long recipeId, String memberId, boolean isViewed);
}
