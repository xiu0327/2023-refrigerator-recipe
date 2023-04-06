package refrigerator.back.ingredient.application.port.in;

import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisteredResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientResponseDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;

import java.util.List;

public interface FindIngredientListUseCase {
    List<IngredientResponseDTO> getIngredientList(IngredientSearchCondition condition, int page, int size);

    List<IngredientResponseDTO> getIngredientListOfAll(String email);

    List<IngredientRegisteredResponseDTO> getIngredientListOfRegistered();
}