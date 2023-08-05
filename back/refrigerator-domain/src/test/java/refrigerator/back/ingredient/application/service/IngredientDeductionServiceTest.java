package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.MyIngredientCollection;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.MyIngredientDto;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.UpdateIngredientVolumePort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

/**
 * 2023.08.05 update
 * 유통기한이 지난 식재료 검사 -> Service 단계가 아닌 쿼리문에서 expireDate 가 현재 날짜보다 큰 식재료만 조회함
 * 자세한 쿼리 조건은 SelectMyIngredientQueryTest 참고
 */
class IngredientDeductionServiceTest {

    public static int count;

    @Test
    @DisplayName("식재료 차감 : 기존 용량이 차감 용량보다 큰 경우")
    void deductionTest_case1() {
        // given
        MyIngredientDto myIngredient = MyIngredientDto.builder()
                .id(1L)
                .name("감자")
                .volume(30.0)
                .unit("g")
                .build();
        IngredientDeductionDTO recipeIngredient = IngredientDeductionDTO.builder()
                .unit("g")
                .name("감자")
                .volume(15.0)
                .build();
        // when
        myIngredient.deduct(recipeIngredient);
        // then
        assertThat(myIngredient.getVolume()).isEqualTo(15.0);
    }
    
    @Test
    @DisplayName("식재료 차감 : 기존 용량이 차감 용량보다 작은 경우 -> 기존 용량 모두 차감")
    void deductionTest_case2() {
        // given
        MyIngredientDto myIngredient = MyIngredientDto.builder()
                .id(1L)
                .name("감자")
                .volume(30.0)
                .unit("g")
                .build();
        IngredientDeductionDTO recipeIngredient = IngredientDeductionDTO.builder()
                .unit("g")
                .name("감자")
                .volume(40.0)
                .build();
        // when
        myIngredient.deduct(recipeIngredient);
        // then
        assertThat(myIngredient.getVolume()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("식재료 리스트 비어있는지 체크 -> 비어있는 Map으로 collection 생성 시 에러 발생")
    void ingredientsEmptyCheckTest() {
        assertThatThrownBy(() -> {
            MyIngredientCollection myIngredients = new MyIngredientCollection(new HashMap<>());
        }).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("용량이 변경된 식재료만 update 쿼리 실행")
    void updateToVolume(){
        // given
        UpdateIngredientVolumePort port = (id, volume) -> count++;
        MyIngredientCollection myIngredientCollection = new MyIngredientCollection(getMyIngredients());
        // when
        myIngredientCollection.updateMyIngredientVolume(port);
        // then
        assertThat(count).isEqualTo(1);
    }

    private Map<String, MyIngredientDto> getMyIngredients() {
        Map<String, MyIngredientDto> myIngredients = new HashMap<>();
        myIngredients.put("안심", new MyIngredientDto(1L, "안심", 20.0, "g", true));
        myIngredients.put("콩나물", new MyIngredientDto(2L, "콩나물", 20.0, "g", false));
        myIngredients.put("소고기", new MyIngredientDto(3L, "소고기", 20.0, "g", false));
        return myIngredients;
    }

}