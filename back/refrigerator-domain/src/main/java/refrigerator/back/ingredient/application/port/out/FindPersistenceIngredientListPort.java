package refrigerator.back.ingredient.application.port.out;


import refrigerator.back.ingredient.application.domain.Ingredient;

import java.util.List;

public interface FindPersistenceIngredientListPort {
    List<Ingredient> getIngredients(String email);
}
