package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.in.suggestIngredient.ProposeIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.suggestedIngredient.SaveSuggestedIngredientPort;

@Service
@RequiredArgsConstructor
@Transactional
public class SuggestIngredientService implements ProposeIngredientUseCase {

    private final SaveSuggestedIngredientPort saveSuggestedIngredientPort;

    @Override
    public void proposeIngredient(String name, String capacityUnit, String email) {
        saveSuggestedIngredientPort.proposeIngredient(
                SuggestedIngredient.builder()
                        .name(name)
                        .unit(capacityUnit)
                        .email(email)
                        .build()
        );
    }
}
