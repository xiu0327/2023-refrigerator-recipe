package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.RecipeIngredientAndCourseCollection;

public interface GetRecipeIngredientAndCourseDataPort {
    RecipeIngredientAndCourseCollection getData(Long recipeId);
}
