package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeDetailDTO;

public interface FindRecipeDetailUseCase {
    InRecipeDetailDTO getRecipe(Long recipeID, boolean isViewed);
}
