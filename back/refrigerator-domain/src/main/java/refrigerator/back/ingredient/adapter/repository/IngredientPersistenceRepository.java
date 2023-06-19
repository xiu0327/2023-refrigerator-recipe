package refrigerator.back.ingredient.adapter.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

import java.util.List;
import java.util.Optional;

public interface IngredientPersistenceRepository extends JpaRepository<Ingredient, Long>{

    /** get */

    List<Ingredient> findByEmailAndDeletedFalse(String email);

    Optional<Ingredient> findByIdAndDeletedFalse(Long id);

    /** batch */

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from Ingredient i where i.deleted = true")
    void deleteIngredient();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from SuggestedIngredient si where si.name = :name")
    void deleteSuggestedIngredient(@Param("name") String name);

}
