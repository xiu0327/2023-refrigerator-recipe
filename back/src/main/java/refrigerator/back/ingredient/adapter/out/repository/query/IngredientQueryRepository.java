package refrigerator.back.ingredient.adapter.out.repository.query;

import com.querydsl.core.Fetchable;
import org.springframework.data.domain.Pageable;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IngredientQueryRepository {

    void saveSuggestIngredient(SuggestedIngredient ingredient);

    void deleteIngredient(Long id);

    void deleteAllIngredients(List<Long> ids);

    // 단건 조회
    Optional<OutIngredientDetailDTO> findIngredient(Long id);

    // 일반 목록 조회
    List<OutIngredientDTO> findIngredientList(IngredientSearchCondition condition, Pageable pageable);

    // 임박 식재료 조회
    List<OutIngredientDTO> findIngredientListByDeadline(LocalDate expirationDate, String email);

    // 식재료 검색
    List<OutIngredientDTO> findIngredientListOfAll(String email);
}
