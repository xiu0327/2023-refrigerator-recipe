package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import refrigerator.back.global.time.CurrentDate;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class IngredientSaveServiceTest {

    @Mock IngredientSaveService ingredientSaveService;

    @Mock CurrentDate currentDate;

    @Mock SaveIngredientPort saveIngredientPort;

    @Test
    @DisplayName("식재료 등록")
    void registerIngredientTest() {

        // given
        given(currentDate.now())
                .willReturn(LocalDate.of(2023,1,1));

        given(saveIngredientPort.saveIngredient(any()))
                .willReturn(1L);

        // when
        IngredientRegisterDTO dto = ingredientSaveService.registerIngredient(
                "감자",
                LocalDate.of(2023,1,1),
                30.0,
                "g",
                IngredientStorageType.FRIDGE,
                1,
                "email123@gmail.com");

        //then
        assertThat(dto.getIngredientID()).isEqualTo(1L);
    }
}