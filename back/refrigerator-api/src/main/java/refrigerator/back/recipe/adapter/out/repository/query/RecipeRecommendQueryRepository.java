package refrigerator.back.recipe.adapter.out.repository.query;

import refrigerator.back.recipe.adapter.out.dto.OutRecipeIngredientNameDTO;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendDTO;

import java.util.List;

public interface RecipeRecommendQueryRepository {
    List<OutRecipeIngredientNameDTO> findRecipeIngredientNames();
    List<String> findIngredientName(String memberId);
    List<OutRecipeRecommendDTO> findRecommendRecipes(List<Long> recipeIds);
}
