package refrigerator.back.ingredient.application.port.out;



import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;

import java.time.LocalDate;
import java.util.List;

public interface ReadIngredientPort {

    Ingredient getIngredient(Long id);

    IngredientDetailResponseDTO getIngredientDetail(Long id);

    List<IngredientResponseDTO> getIngredientList(IngredientSearchCondition condition, int page, int size);

    List<IngredientResponseDTO> getIngredientListOfAll(String email);

    List<IngredientResponseDTO> getIngredientListByDeadline(LocalDate date, String email);

    // 테스트용
    Ingredient getIngredientById(Long id);
}
