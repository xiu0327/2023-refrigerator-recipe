package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentDate;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientDeductionServiceTest {

    @InjectMocks IngredientDeductionService ingredientDeductionService;

    @Mock FindIngredientListPort findIngredientListPort;

    @Mock CurrentDate currentDate;

    @Test
    @DisplayName("식재료 차감 : 기존 용량이 dto 용량보다 큰 경우")
    void deductionTest_case1() {

        // given
        String memberId = "email123@gmail.com";
        Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .name("감자")
                .registrationDate(LocalDate.of(2023,1,1))
                .expirationDate(LocalDate.of(2023,1,1))
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .storageMethod(IngredientStorageType.FRIDGE)
                .email(memberId)
                .build();

        List<Ingredient> ingredients = new ArrayList<>(List.of(ingredient));

        given(findIngredientListPort.getIngredients(memberId))
                .willReturn(ingredients);

        given(currentDate.now())
                .willReturn(LocalDate.of(2022, 12, 31));

        // when
        IngredientDeductionDTO dto = IngredientDeductionDTO.builder()
                .unit("g")
                .name("감자")
                .volume(15.0)
                .build();

        List<IngredientDeductionDTO> dtos = new ArrayList<>(List.of(dto));
        ingredientDeductionService.deduction(memberId, dtos);

        // then
        assertThat(findIngredientListPort.getIngredients(memberId).get(0).getCapacity())
                .isEqualTo(15.0);
    }
    
    @Test
    @DisplayName("식재료 차감 : 기존 용량이 dto 용량보다 작은 경우")
    void deductionTest_case2() {
        // given
        String memberId = "email123@gmail.com";
        Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .name("감자")
                .registrationDate(LocalDate.of(2023,1,1))
                .expirationDate(LocalDate.of(2023,1,1))
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .storageMethod(IngredientStorageType.FRIDGE)
                .email(memberId)
                .build();

        List<Ingredient> ingredients = new ArrayList<>(List.of(ingredient));

        given(findIngredientListPort.getIngredients(memberId))
                .willReturn(ingredients);

        given(currentDate.now())
                .willReturn(LocalDate.of(2022, 12, 31));    // 2023.1.1 이전

        // when
        IngredientDeductionDTO dto = IngredientDeductionDTO.builder()
                .unit("g")
                .name("감자")
                .volume(40.0)
                .build();

        List<IngredientDeductionDTO> dtos = new ArrayList<>(List.of(dto));
        ingredientDeductionService.deduction(memberId, dtos);

        // then
        assertThat(findIngredientListPort.getIngredients(memberId).get(0).getCapacity())
                .isEqualTo(0.0);
    }

    @Test
    @DisplayName("식재료 차감 실패 : 유통기한 지난 식재료")
    void deductionTest_case3() {
        // given
        String memberId = "email123@gmail.com";
        Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .name("감자")
                .registrationDate(LocalDate.of(2023,1,1))
                .expirationDate(LocalDate.of(2023,1,1))
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .storageMethod(IngredientStorageType.FRIDGE)
                .email(memberId)
                .build();

        List<Ingredient> ingredients = new ArrayList<>(List.of(ingredient));

        given(findIngredientListPort.getIngredients(memberId))
                .willReturn(ingredients);
        given(currentDate.now())
                .willReturn(LocalDate.of(2023, 1, 2));

        // when, then
        IngredientDeductionDTO dto = IngredientDeductionDTO.builder()
                .unit("g")
                .name("감자")
                .volume(40.0)
                .build();

        List<IngredientDeductionDTO> dtos = new ArrayList<>(List.of(dto));

        assertThatThrownBy(() -> ingredientDeductionService.deduction(memberId, dtos))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("식재료 리스트 비어있는지 체크")
    void ingredientsEmptyCheckTest() {
        // given
        String memberId = "email123@gmail.com";
        given(findIngredientListPort.getIngredients(memberId))
                .willReturn(new ArrayList<Ingredient>());

        // when, then
        assertThatThrownBy(() -> ingredientDeductionService
                .IngredientsEmptyCheck(findIngredientListPort.getIngredients(memberId)))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("식재료 List -> Map 변환 체크")
    void toIngredientMapTest() {
        // given
        IngredientDeductionDTO dto = IngredientDeductionDTO.builder()
                .unit("g")
                .name("감자")
                .volume(40.0)
                .build();

        List<IngredientDeductionDTO> dtos = new ArrayList<>(List.of(dto));

        // when
        Map<String, Double> map = ingredientDeductionService.toIngredientMap(dtos);

        // then
        assertThat(map.containsKey(dto.getName())).isTrue();
        assertThat(map.get(dto.getName())).isEqualTo(40.0);
    }

    @Test
    @DisplayName("식재료 목록 중 유톻기한이 지나지 않은 식재료 추출")
    void extractAvailableIngredientsTest() {

        // given
        LocalDate now = LocalDate.of(2023,1,2);

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .id(1L)
                .name("감자")
                .registrationDate(LocalDate.of(2023, 1, 1))
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .storageMethod(IngredientStorageType.FRIDGE)
                .email("email123@gmail.com");

        Ingredient ingredient1 = builder.expirationDate(LocalDate.of(2023, 1, 1)).build();
        Ingredient ingredient2 = builder.expirationDate(LocalDate.of(2023, 1, 3)).build();

        List<Ingredient> ingredients = new ArrayList<>(List.of(ingredient1, ingredient2));

        // when
        List<Ingredient> availableIngredients = ingredientDeductionService.extractAvailableIngredients(now, ingredients);

        // then
        assertThat(availableIngredients.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("dto의 식재료가 내 식재료 목록에 모두 포함되는지 체크")
    void dtoContainCheckBySafeIngredientTest() {

        //given
        List<String> ingredients = new ArrayList<>(List.of("감자", "고구마"));

        Map<String, Double> ingredientDTOMap = new HashMap<>();
        ingredientDTOMap.put("감자", 40.0);
        ingredientDTOMap.put("자색고구마", 40.0);

        // when, then
        assertThatThrownBy(() -> ingredientDeductionService.dtoContainCheckBySafeIngredient(ingredientDTOMap, ingredients))
                .isInstanceOf(BusinessException.class);
    }
}