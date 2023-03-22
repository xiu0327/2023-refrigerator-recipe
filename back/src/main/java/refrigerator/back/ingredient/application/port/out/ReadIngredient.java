package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.IngredientDomain;

public interface ReadIngredient {

    IngredientDomain findOne(Long id);
}
