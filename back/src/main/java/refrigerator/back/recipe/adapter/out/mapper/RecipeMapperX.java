package refrigerator.back.recipe.adapter.out.mapper;

import refrigerator.back.recipe.adapter.out.entity.Recipe;
import refrigerator.back.recipe.adapter.out.entity.RecipeCourse;
import refrigerator.back.recipe.adapter.out.entity.RecipeIngredient;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredientDomain;

public interface RecipeMapperX {
    RecipeDomain toRecipeDomain(Recipe entity);
    Recipe toRecipeEntity(RecipeDomain domain);
    RecipeCourseDomain toRecipeCourseDomain(RecipeCourse entity);
    RecipeIngredientDomain toRecipeIngredientDomain(RecipeIngredient entity);
}
