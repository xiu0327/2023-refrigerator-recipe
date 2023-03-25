package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;

import java.util.List;

public interface SearchRecipePort {
    List<InRecipeDTO> search(RecipeSearchCondition condition, int page, int size);
    List<String> findRecipeFoodTypeCond();
    List<String> findRecipeCategoryCond();
}
