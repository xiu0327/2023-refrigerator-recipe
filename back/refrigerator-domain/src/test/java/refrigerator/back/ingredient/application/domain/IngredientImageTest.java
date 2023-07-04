package refrigerator.back.ingredient.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IngredientImageTest {

    @Test
    @DisplayName("식재료 이미지 테스트")
    void ingredientImageTest(){
        IngredientImage domain = IngredientImage.builder()
                .id(1)
                .typeName("테스트 분류")
                .imageFileName("test.png")
                .build();

        assertThat(domain.getId()).isEqualTo(1);
        assertThat(domain.getTypeName()).isEqualTo("테스트 분류");
        assertThat(domain.getImageFileName()).isEqualTo("test.png");
    }
}