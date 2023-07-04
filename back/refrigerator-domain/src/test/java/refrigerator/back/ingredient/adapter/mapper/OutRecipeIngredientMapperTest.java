package refrigerator.back.ingredient.adapter.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.dto.OutRecipeIngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;

import static org.assertj.core.api.Assertions.*;

class OutRecipeIngredientMapperTest {
    OutRecipeIngredientMapper outRecipeIngredientMapper = Mappers.getMapper(OutRecipeIngredientMapper.class);

    @Test
    @DisplayName("OutRecipeIngredientDTO에서 RecipeIngredientDto로 변환")
    void toRecipeIngredientDto() {
        OutRecipeIngredientDTO outDto = new OutRecipeIngredientDTO(1L, "감자");

        RecipeIngredientDto dto = outRecipeIngredientMapper.toRecipeIngredientDto(outDto);

        assertThat(dto.getIngredientId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
    }
}