package refrigerator.back.recipe.adapter.out.repository.query;

import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RecipeRecommendQueryRepository {
    Map<Long, Set<String>> findRecipeIngredientNames();
    List<String> findIngredientName(String memberId);
    List<OutRecipeRecommendDTO> findRecommendRecipes(List<Long> recipeIds);
}
