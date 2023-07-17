package refrigerator.back.ingredient.outbound.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OutIngredientInRecipeDTOTest {

    @Test
    @DisplayName("outIngredientInRecipeDTO 테스트")
    void outIngredientInRecipeDTOTest() {
        OutIngredientInRecipeDTO ingredient = new OutIngredientInRecipeDTO(1L, "감자");
        
        assertThat(ingredient.getIngredientId()).isEqualTo(1L);
        assertThat(ingredient.getName()).isEqualTo("감자");
    }
}