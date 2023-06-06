package refrigerator.back.recipe.application.port.in;


import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;

import java.util.List;

public interface SearchRecipeUseCase {
    List<InRecipeDTO> search(RecipeSearchCondition condition, int page, int size);
}
