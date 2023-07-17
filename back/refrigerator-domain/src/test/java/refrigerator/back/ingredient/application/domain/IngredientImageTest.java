package refrigerator.back.ingredient.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class IngredientImageTest {


    @Test
    @DisplayName("식재료 이미지 도메인 테스트")
    void ingredientImageTest() {
        IngredientImage image = IngredientImage.builder()
                .id(1)
                .imageFileName("test.png")
                .typeName("testType")
                .build();

        assertThat(image.getId()).isEqualTo(1L);
        assertThat(image.getImageFileName()).isEqualTo("test.png");
        assertThat(image.getTypeName()).isEqualTo("testType");
    }
}