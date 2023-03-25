package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.adapter.in.dto.InRecipeBasicListDTO;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;

public interface SearchRecipeUseCase {
    InRecipeBasicListDTO<InRecipeDTO> search(RecipeSearchCondition condition, int page, int size);
}
