package refrigerator.back.ingredient.application.port.out;



import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;

import java.time.LocalDate;
import java.util.List;

public interface ReadIngredientPort {

    Ingredient getIngredient(Long id);

    IngredientDetailDTO getIngredientDetail(Long id);

    List<IngredientDTO> getIngredientList(IngredientSearchCondition condition, int page, int size);

    List<IngredientDTO> getIngredientListOfAll(String email);

    List<IngredientDTO> getIngredientListByDeadline(LocalDate date, String email);

    // 테스트용
    Ingredient getIngredientById(Long id);
}
