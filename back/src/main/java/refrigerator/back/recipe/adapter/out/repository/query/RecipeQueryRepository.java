package refrigerator.back.recipe.adapter.out.repository.query;

import org.springframework.data.domain.Pageable;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeDTO;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeDetailDTO;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;

import java.util.List;

public interface RecipeQueryRepository {
    Recipe findRecipeById(Long recipeID);
    OutRecipeDetailDTO findRecipeDetails(Long recipeID);
    List<RecipeCourse> findRecipeCourse(Long recipeID);
    List<OutRecipeDTO> findRecipeList(Pageable pageable);
}
