package refrigerator.back.ingredient.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.out.repository.IngredientRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.port.out.FindPersistenceIngredientListPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IngredientByRecipeAdapter implements FindPersistenceIngredientListPort {

    private final IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> getIngredients(String email) {
        return ingredientRepository.findByEmailAndDeletedFalse(email);
    }
}
