package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReadRecipePort {
    Recipe getRecipeDetails(Long recipeID);
    List<RecipeCourse> getRecipeCourse(Long recipeID);
    List<InRecipeDTO> getRecipeList(int page, int size);
}
