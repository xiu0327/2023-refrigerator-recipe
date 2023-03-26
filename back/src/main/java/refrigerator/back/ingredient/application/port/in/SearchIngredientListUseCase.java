package refrigerator.back.ingredient.application.port.in;

import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;

import java.util.List;

public interface SearchIngredientListUseCase {
    public List<IngredientResponseDTO> searchList(String name, String email);
}
