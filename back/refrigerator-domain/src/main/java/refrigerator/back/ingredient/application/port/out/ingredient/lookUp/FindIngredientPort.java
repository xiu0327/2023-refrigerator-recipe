package refrigerator.back.ingredient.application.port.out.ingredient.lookUp;

import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;

public interface FindIngredientPort {

    Ingredient getIngredient(Long id);

    IngredientDetailDTO getIngredientDetail(Long id);

}
