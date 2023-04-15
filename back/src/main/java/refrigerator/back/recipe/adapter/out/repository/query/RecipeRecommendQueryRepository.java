package refrigerator.back.recipe.adapter.out.repository.query;

import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendDTO;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeRecommendIngredientDTO;

import java.util.List;

public interface RecipeRecommendQueryRepository {
    List<OutRecipeRecommendIngredientDTO> findRecipeNameAndIngredientList();
    List<String> findIngredientNameByMember(String memberId);
    List<OutRecipeRecommendDTO> findRecommendRecipeListById(List<Long> recipeIds);
}
