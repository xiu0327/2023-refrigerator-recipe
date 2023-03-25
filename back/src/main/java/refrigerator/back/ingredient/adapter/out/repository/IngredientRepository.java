package refrigerator.back.ingredient.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


}
