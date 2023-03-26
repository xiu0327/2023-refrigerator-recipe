package refrigerator.back.ingredient.application.port.in;

import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;

public interface LookUpIngredientDetailUseCase {
    IngredientDetailResponseDTO lookUpDetail(Long idm, String email);
}
