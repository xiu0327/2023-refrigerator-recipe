package refrigerator.back.ingredient.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.adapter.out.persistence.IngredientAdapter;
import refrigerator.back.ingredient.application.domain.Ingredient;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class IngredientServiceTest {

    @Autowired IngredientService ingredientService;
    @Autowired IngredientAdapter ingredientAdapter;

    @Test
    void 식재료_등록() {
        Long id = ingredientService.register("당근", LocalDateTime.now(), "30",
                "개", "냉장", "ehgus5825@naver.com");

        Ingredient findIngredient = ingredientAdapter.findOne(id);

        assertThat(findIngredient.getName()).isEqualTo("당근");
        assertThat(findIngredient.getCapacity()).isEqualTo("30");
    }

    @Test
    void 식재료_수정() {
        Long id = ingredientService.register("당근", LocalDateTime.now(), "30",
                "개", "냉장", "ehgus5825@naver.com");

        ingredientService.modify(id, LocalDateTime.of(2023, 3,24,0,0,0),
                "40", "냉동", "ehgus5825@naver.com");

        Ingredient findIngredient = ingredientAdapter.findOne(id);
        assertThat(findIngredient.getName()).isEqualTo("당근");
        assertThat(findIngredient.getCapacity()).isEqualTo("40");
        assertThat(findIngredient.getExpirationDate()).isEqualTo(LocalDateTime.of(2023, 3,24,0,0,0));
        assertThat(findIngredient.getStorageMethod()).isEqualTo("냉동");
    }

    @Test
    void 식재료_수정_예외() {
        Long id = ingredientService.register("당근", LocalDateTime.now(), "30",
                "개", "냉장", "ehgus5825@naver.com");

        assertThatThrownBy(() -> ingredientService.modify(id,
                LocalDateTime.of(2023, 3,24,0,0,0),
                "40", "냉동", "ehgus5825@gmail.com"))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 식재료_삭제() {
        Long id = ingredientService.register("당근", LocalDateTime.now(), "30",
                "개", "냉장", "ehgus5825@naver.com");

        ingredientService.remove(id, "ehgus5825@naver.com");

        Ingredient findIngredient = ingredientAdapter.findOne(id);
        assertThat(findIngredient).isNull();
    }

    @Test
    void 식재료_삭제_예외() {
        Long id = ingredientService.register("당근", LocalDateTime.now(), "30",
                "개", "냉장", "ehgus5825@naver.com");

        assertThatThrownBy(() -> ingredientService.remove(id, "ehgus5825@gmail.com"))
                .isInstanceOf(BusinessException.class);
    }
}