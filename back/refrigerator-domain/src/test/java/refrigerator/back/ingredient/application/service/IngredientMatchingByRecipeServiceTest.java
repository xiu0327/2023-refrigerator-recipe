package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.time.CurrentDate;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;
import refrigerator.back.ingredient.application.port.out.recipeIngredient.FindRecipeIngredientPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientMatchingByRecipeServiceTest {

    @InjectMocks IngredientMatchingByRecipeService ingredientMatchingByRecipeService;

    @Mock FindRecipeIngredientPort findRecipeIngredientPort;

    @Mock FindIngredientListPort findIngredientListPort;

    @Mock CurrentDate currentDate;

    @Test
    @DisplayName("레시피 식재료 ID 반환 테스트")
    void getIngredientIdsTest(){
        String memberId = "email123@gmail.com";

        IngredientDTO.IngredientDTOBuilder builder = IngredientDTO.builder()
                .remainDays(0)
                .image("test.png");

        LocalDate now = LocalDate.of(2023, 1,1);

        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        ingredientDTOList.add(builder.ingredientID(1L).name("감자").build());
        ingredientDTOList.add(builder.ingredientID(2L).name("고구마").build());
        ingredientDTOList.add(builder.ingredientID(3L).name("자색고구마").build());
        ingredientDTOList.add(builder.ingredientID(4L).name("옥수수").build());

        given(currentDate.now())
                .willReturn(now);

        given(findIngredientListPort.getIngredientListOfAll(now, memberId))
                .willReturn(ingredientDTOList);

        List<RecipeIngredientDto> recipeIngredientDtos = new ArrayList<>();

        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(5L).name("감자").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(6L).name("고구마").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(7L).name("자색고구마").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(8L).name("옥수수").build());

        given(findRecipeIngredientPort.getRecipeIngredient(1L))
                .willReturn(recipeIngredientDtos);

        List<Long> ingredientIds = ingredientMatchingByRecipeService.getIngredientIds(memberId, 1L);
        assertThat(ingredientIds.size()).isEqualTo(4);
        assertThat(ingredientIds.get(0)).isEqualTo(5L);
        assertThat(ingredientIds.get(1)).isEqualTo(6L);
        assertThat(ingredientIds.get(2)).isEqualTo(7L);
        assertThat(ingredientIds.get(3)).isEqualTo(8L);
    }
}