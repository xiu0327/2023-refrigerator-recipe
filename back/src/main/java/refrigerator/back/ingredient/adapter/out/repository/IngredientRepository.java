package refrigerator.back.ingredient.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.ingredient.adapter.out.entity.IngredientEntity;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
}
