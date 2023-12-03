package refrigerator.back.ingredient.application.port.admin;

import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

public interface SaveRegisteredIngredientPort {

    Long saveRegisteredIngredient(RegisteredIngredient registeredIngredient);
}
