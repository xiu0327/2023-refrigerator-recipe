package refrigerator.back.ingredient.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IngredientRegisterDTOTest {

    @Test
    @DisplayName("식재료 등록 DTO 테스트")
    void ingredientRegisterDTOTest() {
        IngredientRegisterDTO dto = IngredientRegisterDTO.builder()
                .ingredientID(1L)
                .build();

        assertThat(dto.getIngredientID()).isEqualTo(1L);
    }
}