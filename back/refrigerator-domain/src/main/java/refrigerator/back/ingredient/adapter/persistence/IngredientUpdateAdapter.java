package refrigerator.back.ingredient.adapter.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.repository.IngredientQueryRepository;
import refrigerator.back.ingredient.adapter.repository.IngredientPersistenceRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.port.out.ingredient.update.DeleteIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class IngredientUpdateAdapter implements DeleteIngredientPort, SaveIngredientPort {

    private final IngredientPersistenceRepository ingredientRepository;
    private final IngredientQueryRepository ingredientQueryRepository;

    @Override
    public Long saveIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return ingredient.getId();
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientQueryRepository.deleteIngredient(id);
    }

    @Override
    public void deleteAllIngredients(List<Long> ids) {
        ingredientQueryRepository.deleteAllIngredients(ids);
    }

}
