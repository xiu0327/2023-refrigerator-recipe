package refrigerator.back.recipe.adapter.out.repository;

import org.springframework.data.domain.Pageable;
import refrigerator.back.recipe.adapter.out.dto.RecipeListMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeMappingDTO;
import refrigerator.back.recipe.adapter.out.entity.RecipeCourse;
import refrigerator.back.recipe.adapter.out.dto.RecipeSearchConditionDTO;

import java.util.List;

public interface RecipeQueryRepository {
    RecipeMappingDTO findRecipeByID(Long recipeID);
    List<RecipeCourse> findRecipeCourse(Long recipeID);
    List<RecipeListMappingDTO> findRecipeList(Pageable pageable);
}
