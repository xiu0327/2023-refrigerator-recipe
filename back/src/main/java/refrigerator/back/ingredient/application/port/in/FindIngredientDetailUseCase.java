package refrigerator.back.ingredient.application.port.in;

import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;

public interface FindIngredientDetailUseCase {
    IngredientDetailResponseDTO getIngredient(Long id);
}
