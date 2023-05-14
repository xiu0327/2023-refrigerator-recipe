package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeDetailDTO;
import refrigerator.back.recipe.application.domain.entity.Recipe;

public interface FindRecipeDetailUseCase {
    Recipe getRecipe(Long recipeID, boolean isViewed);
}
