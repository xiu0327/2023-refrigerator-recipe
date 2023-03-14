package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;

import java.util.List;

public interface FindRecipeCourseUseCase {
    List<RecipeCourseDomain> getRecipeCourse(Long recipeID);
}
