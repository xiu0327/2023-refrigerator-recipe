package refrigerator.back.ingredient.application.port.in;


import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;

public interface FindIngredientDetailUseCase {
    IngredientDetailDTO getIngredient(Long id);
}
