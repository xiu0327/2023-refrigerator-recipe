package refrigerator.back.ingredient.adapter.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.adapter.repository.IngredientUpdateQueryRepository;
import refrigerator.back.ingredient.adapter.repository.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.port.batch.DeleteIngredientBatchPort;

@Slf4j
@Component
@RequiredArgsConstructor
public class IngredientBatchAdapter implements DeleteIngredientBatchPort {

    private final IngredientUpdateQueryRepository ingredientUpdateQueryRepository;
    private final SubIngredientQueryRepository subIngredientQueryRepository;

    @Override
    public Long deleteIngredients() {
        return ingredientUpdateQueryRepository.deleteIngredients();
    }

    @Override
    public Long deleteSuggestedIngredient(String name) {
        return subIngredientQueryRepository.deleteSuggestedIngredient(name);
    }
}
