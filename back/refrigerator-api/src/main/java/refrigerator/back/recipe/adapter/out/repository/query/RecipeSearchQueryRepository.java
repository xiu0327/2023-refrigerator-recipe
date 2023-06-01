package refrigerator.back.recipe.adapter.out.repository.query;

import org.springframework.data.domain.Pageable;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.RecipeCategory;
import refrigerator.back.recipe.application.domain.entity.RecipeFoodType;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;

import java.util.List;

public interface RecipeSearchQueryRepository {
    List<OutRecipeDTO> searchRecipe(RecipeSearchCondition condition, Pageable pageable);
    List<RecipeFoodType> findRecipeFoodTypeList();
    List<RecipeCategory> findRecipeCategoryList();
}
