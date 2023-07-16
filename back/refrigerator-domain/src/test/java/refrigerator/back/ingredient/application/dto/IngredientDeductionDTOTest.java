package refrigerator.back.ingredient.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IngredientDeductionDTOTest {

    @Test
    @DisplayName("식재료 차감 DTO 테스트")
    void ingredientDeductionDTOTest() {
        IngredientDeductionDTO dto = IngredientDeductionDTO.builder()
                .name("감자")
                .volume(30.0)
                .unit("g")
                .build();

        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getVolume()).isEqualTo(30.0);
        assertThat(dto.getUnit()).isEqualTo("g");
    }
}