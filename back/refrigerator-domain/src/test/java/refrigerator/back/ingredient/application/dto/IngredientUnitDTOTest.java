package refrigerator.back.ingredient.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IngredientUnitDTOTest {
    
    @Test
    @DisplayName("식재료 단위 DTO 테스트")
    void ingredientUnitDTOTest(){
        IngredientUnitDTO dto = IngredientUnitDTO.builder()
                .name("감자")
                .unit("g")
                .build();

        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getUnit()).isEqualTo("g");

    }
}