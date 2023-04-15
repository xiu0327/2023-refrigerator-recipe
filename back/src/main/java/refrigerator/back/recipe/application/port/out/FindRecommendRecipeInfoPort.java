package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FindRecommendRecipeInfoPort {
    List<InRecipeRecommendDTO> findRecommendRecipeInfo(List<Long> recipeIds);
    Map<Long, Set<String>> getRecipeIngredientNameList();
}
