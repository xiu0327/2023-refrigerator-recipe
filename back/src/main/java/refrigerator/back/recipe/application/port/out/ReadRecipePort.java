package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;

import java.util.List;

public interface ReadRecipePort {
    RecipeDomain getRecipeDetails(Long recipeID);
    List<RecipeCourseDomain> getRecipeCourse(Long recipeID);
    List<RecipeDomain> getRecipeList(int page, int size);
}
