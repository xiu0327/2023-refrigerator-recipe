package refrigerator.back.ingredient.application.port.in;

import java.util.List;

public interface MatchIngredientByRecipeUseCase {
    List<Long> getData(String memberId, Long recipeId);
}
