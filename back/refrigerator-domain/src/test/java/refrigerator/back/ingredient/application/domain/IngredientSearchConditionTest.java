package refrigerator.back.ingredient.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IngredientSearchConditionTest {
    
    @Test
    @DisplayName("식재료 검색 조건 테스트")
    void ingredientSearchConditionTest() {

        IngredientSearchCondition condition = IngredientSearchCondition.builder()
                .email("email123@gmail.com")
                .storage(IngredientStorageType.FRIDGE)
                .deadline(false)
                .build();

        assertThat(condition.getStorage()).isEqualTo(IngredientStorageType.FRIDGE);
        assertThat(condition.getEmail()).isEqualTo("email123@gmail.com");
        assertThat(condition.getDeadline()).isEqualTo(false);
    }
    
    
}