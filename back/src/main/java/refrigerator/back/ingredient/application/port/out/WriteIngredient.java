package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.Ingredient;

public interface WriteIngredient {

    Long save(Ingredient save);

    void update(Ingredient ingredient);

    void delete(Ingredient ingredient);
}
