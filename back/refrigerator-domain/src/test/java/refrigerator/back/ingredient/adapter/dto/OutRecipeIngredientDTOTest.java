package refrigerator.back.ingredient.adapter.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OutRecipeIngredientDTOTest {

    @Test
    @DisplayName("레시피 식재료 outDTO 테스트")
    void outRecipeIngredientDTOTest() {
        OutRecipeIngredientDTO dto = new OutRecipeIngredientDTO(1L, "감자");
        assertThat(dto.getIngredientId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
    }
}