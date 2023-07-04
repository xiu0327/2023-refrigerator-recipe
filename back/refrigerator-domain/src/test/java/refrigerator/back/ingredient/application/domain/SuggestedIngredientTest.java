package refrigerator.back.ingredient.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SuggestedIngredientTest {

    @Test
    @DisplayName("요청된 식재료 도메인 테스트")
    void suggestedIngredientTest() {
        SuggestedIngredient domain = SuggestedIngredient.builder()
                .id(1L)
                .name("감자")
                .email("email123@gmail.com")
                .unit("g")
                .build();

        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("감자");
        assertThat(domain.getUnit()).isEqualTo("g");
        assertThat(domain.getEmail()).isEqualTo("email123@gmail.com");
    }

}