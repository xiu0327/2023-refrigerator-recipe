package refrigerator.back.ingredient.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import refrigerator.back.ingredient.adapter.out.repository.query.IngredientQueryRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;


import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientQueryRepository {

    // 식재료 요청시 list 확인용 (테스트)
    @Query("select si from SuggestedIngredient si")
    List<SuggestedIngredient> findSuggestedIngredientList();

    // 식재료 차감시 냉장고 재료 불러오기
    List<Ingredient> findByEmailAndDeletedFalse(String email);

    // 식재료 수정시 기존 식재료 불러오기 용
    Optional<Ingredient> findByIdAndDeletedFalse(Long id);

}
