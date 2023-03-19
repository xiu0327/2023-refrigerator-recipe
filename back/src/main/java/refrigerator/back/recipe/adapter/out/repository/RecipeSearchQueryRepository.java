package refrigerator.back.recipe.adapter.out.repository;

import org.springframework.data.domain.Pageable;
import refrigerator.back.recipe.adapter.out.dto.RecipeListMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeSearchConditionDTO;
import refrigerator.back.recipe.adapter.out.entity.RecipeCategory;
import refrigerator.back.recipe.adapter.out.entity.RecipeFoodType;

import java.util.List;

public interface RecipeSearchQueryRepository {
    List<RecipeListMappingDTO> searchRecipe(RecipeSearchConditionDTO recipeSearch, Pageable pageable);
    List<RecipeFoodType> findRecipeFoodTypeList();
    List<RecipeCategory> findRecipeCategoryList();
}
