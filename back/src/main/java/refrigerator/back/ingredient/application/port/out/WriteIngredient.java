package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

public interface WriteIngredient {

    Long saveIngredient(Ingredient ingredient);

    void proposeIngredient(SuggestedIngredient ingredient);
}
