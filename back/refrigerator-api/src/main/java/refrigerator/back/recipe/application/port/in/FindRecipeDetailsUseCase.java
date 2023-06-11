package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeDto;

public interface FindRecipeDetailsUseCase {
    InRecipeDto findRecipeDetails(Long recipeId, String memberId);
}
