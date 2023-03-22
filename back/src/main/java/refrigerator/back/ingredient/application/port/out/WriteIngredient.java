package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.IngredientDomain;

public interface WriteIngredient {

    Long save(IngredientDomain save);

    void update(IngredientDomain ingredient);

    void delete(Long id, String email);
}
