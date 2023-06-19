package refrigerator.back.ingredient.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.adapter.repository.IngredientQuerySubRepository;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.out.suggestedIngredient.SaveSuggestedIngredientPort;

@Repository
@RequiredArgsConstructor
public class SuggestedIngredientAdapter implements SaveSuggestedIngredientPort {

    private final IngredientQuerySubRepository IngredientSubQueryRepository;

    @Override
    public void proposeIngredient(SuggestedIngredient ingredient) {
        IngredientSubQueryRepository.saveSuggestIngredient(ingredient);
    }
}
