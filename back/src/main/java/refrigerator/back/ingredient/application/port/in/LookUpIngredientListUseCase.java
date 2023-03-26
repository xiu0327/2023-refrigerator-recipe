package refrigerator.back.ingredient.application.port.in;

import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;

import java.util.List;

public interface LookUpIngredientListUseCase {
    List<IngredientResponseDTO> lookUpList(String storage, boolean deadline, String email);
}
