package refrigerator.back.ingredient.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.out.repository.query.IngredientQueryRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientQueryRepository {

    List<Ingredient> findByEmailAndDeletedFalseOrderByNameAsc(String email); // 회원 id와 식재료 중 delete 되지 않은 것

    Optional<Ingredient> findByIdAndDeletedFalse(Long id);

    @Query("select si from SuggestedIngredient si")
    List<SuggestedIngredient> findSuggestedIngredientList();

    List<Ingredient> findByExpirationDateAndEmail(LocalDate expirationDate, String email);

}
