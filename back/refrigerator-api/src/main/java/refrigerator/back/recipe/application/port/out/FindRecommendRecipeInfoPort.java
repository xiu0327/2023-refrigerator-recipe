package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.application.domain.RecipeRecommend;

import java.util.List;
import java.util.Map;

public interface FindRecommendRecipeInfoPort {
    List<InRecipeRecommendDTO> findRecipeByIds(Map<Long, Double> recipeIds);
    Map<Long, RecipeRecommend> findRecipeIngredientNames();
}
