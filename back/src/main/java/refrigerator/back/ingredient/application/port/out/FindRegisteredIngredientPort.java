package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.adapter.in.dto.response.IngredientUnitResponseDTO;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

import java.util.List;

public interface FindRegisteredIngredientPort {
    List<RegisteredIngredient> findIngredientList();

    RegisteredIngredient findIngredient(String name);
}
