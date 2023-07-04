package refrigerator.back.ingredient.application.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RecipeIngredientDtoTest {

    @Test
    @DisplayName("레시피 식재료 DTO 테스트")
    void ingredientUnitDTOTest(){
        RecipeIngredientDto dto = RecipeIngredientDto.builder()
                .ingredientId(1L)
                .name("감자")
                .build();

        assertThat(dto.getIngredientId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
    }
}