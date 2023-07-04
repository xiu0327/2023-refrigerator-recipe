package refrigerator.back.ingredient.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.out.suggestedIngredient.SaveSuggestedIngredientPort;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SuggestIngredientServiceTest {

    @InjectMocks SuggestIngredientService suggestIngredientService;

    @Mock SaveSuggestedIngredientPort saveSuggestedIngredientPort;

    @Test
    void 식재료_요청 () {

        given(saveSuggestedIngredientPort.proposeIngredient(any()))
                .willReturn(1L);

        assertThat(suggestIngredientService.proposeIngredient("감자", "g", "email123@gmail.com"))
                .isEqualTo(1L);
    }
}