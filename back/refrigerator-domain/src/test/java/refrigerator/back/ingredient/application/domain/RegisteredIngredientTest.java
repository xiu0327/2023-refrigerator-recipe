package refrigerator.back.ingredient.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RegisteredIngredientTest {

    @Test
    @DisplayName("등록된 식재료 도메인 테스트")
    void registeredIngredientTest() {
        RegisteredIngredient domain = RegisteredIngredient.builder()
                .id(1L)
                .name("감자")
                .image(1)
                .unit("g")
                .build();

        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("감자");
        assertThat(domain.getImage()).isEqualTo(1);
        assertThat(domain.getUnit()).isEqualTo("g");
    }
    
}