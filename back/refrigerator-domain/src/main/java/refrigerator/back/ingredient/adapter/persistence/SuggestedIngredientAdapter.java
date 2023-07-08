package refrigerator.back.ingredient.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.adapter.repository.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.out.suggestedIngredient.SaveSuggestedIngredientPort;

@Component
@RequiredArgsConstructor
public class SuggestedIngredientAdapter implements SaveSuggestedIngredientPort {

    private final SubIngredientQueryRepository subIngredientQueryRepository;

    @Override
    public Long proposeIngredient(SuggestedIngredient ingredient) {
        return subIngredientQueryRepository.saveSuggestIngredient(ingredient);
    }
}
