package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

public interface WriteIngredientPort {

    Long saveIngredient(Ingredient ingredient);

    void proposeIngredient(SuggestedIngredient ingredient);
}
