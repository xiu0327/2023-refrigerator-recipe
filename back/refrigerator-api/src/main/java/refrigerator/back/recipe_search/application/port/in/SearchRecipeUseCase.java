package refrigerator.back.recipe_search.application.port.in;


import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchDto;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;

import java.util.List;

public interface SearchRecipeUseCase {
    List<InRecipeSearchDto> search(RecipeSearchCondition condition, int page, int size);
    List<InRecipeSearchDto> normalSearch(int page, int size);
}
