package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.in.dto.request.RecipeIngredientVolumeDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.port.in.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.out.FindPersistenceIngredientListPort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class IngredientDeductionServiceTest {

    @Autowired DeductionIngredientVolumeUseCase deductionIngredientVolumeUseCase;
    @Autowired FindPersistenceIngredientListPort findPersistenceIngredientListPort;
    @Autowired TestData testData;

    @Test
    @DisplayName("식재료 차감 -> 기존 용량 >= 레시피 용량 ")
    void deduction_case1() {
        // given
        String memberId = testData.createMemberByEmail("email@gmail.com");

        List<RecipeIngredientVolumeDTO> ingredients = new ArrayList<>();
        double volume = 60.0;
        ingredients.add(createRecipeIngredient("안심", volume, "g"));
        testData.createIngredient("안심", memberId);

        double originalVolume = 70.0;
        // when
        deductionIngredientVolumeUseCase.deduction(memberId, ingredients);
        // then
        List<Ingredient> result = findPersistenceIngredientListPort.getIngredients(memberId);
        for (Ingredient ingredient : result) {
            assertThat(ingredient.getCapacity()).isEqualTo(originalVolume - volume);
        }
    }

    @Test
    @DisplayName("식재료 차감 -> 기존 용량 < 레시피 용량")
    void deduction_case2() {
        // given
        String memberId = testData.createMemberByEmail("email@gmail.com");

        List<RecipeIngredientVolumeDTO> ingredients = new ArrayList<>();
        double volume = 80.0;
        ingredients.add(createRecipeIngredient("안심", volume, "g"));
        testData.createIngredient("안심", memberId);

        // when
        deductionIngredientVolumeUseCase.deduction(memberId, ingredients);
        // then
        List<Ingredient> result = findPersistenceIngredientListPort.getIngredients(memberId);
        for (Ingredient ingredient : result) {
            assertThat(ingredient.getCapacity()).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("식재료 차감 실패 -> 유통기한 지난 식재료")
    void deduction_case3() {
        String memberId = testData.createMemberByEmail("email@gmail.com");

        List<RecipeIngredientVolumeDTO> ingredients = new ArrayList<>();

        double volume = 60.0;
        ingredients.add(createRecipeIngredient("안심", volume, "g"));
        testData.createIngredientDetail("안심", 5,  memberId);

        assertThatThrownBy(() -> deductionIngredientVolumeUseCase.deduction(memberId, ingredients))
                .isInstanceOf(BusinessException.class);
    }

    private RecipeIngredientVolumeDTO createRecipeIngredient(String name, double volume, String unit) {
        return RecipeIngredientVolumeDTO.builder()
                .name(name)
                .volume(volume)
                .unit(unit).build();
    }
}