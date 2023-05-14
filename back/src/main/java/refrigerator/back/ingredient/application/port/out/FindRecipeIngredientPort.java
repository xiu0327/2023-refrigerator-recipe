package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.RecipeIngredientDto;

import java.util.List;

public interface FindRecipeIngredientPort {
    List<RecipeIngredientDto> getData(Long recipeId);
}
