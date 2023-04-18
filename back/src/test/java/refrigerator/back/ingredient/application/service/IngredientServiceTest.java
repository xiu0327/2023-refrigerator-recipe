package refrigerator.back.ingredient.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.adapter.out.persistence.IngredientAdapter;
import refrigerator.back.ingredient.adapter.out.repository.IngredientRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class IngredientServiceTest {

    @Autowired IngredientUpdateService ingredientService;
    @Autowired
    IngredientAdapter ingredientAdapter;
    @Autowired IngredientRepository ingredientRepository;

    @Test
    void 식재료_등록() {
        Long id = ingredientService.registerIngredient("당근", LocalDate.now(), 30,
                "개", "냉장", 1L, "ehgus5825@naver.com");

        Ingredient findIngredient = ingredientAdapter.getIngredientById(id);

        assertThat(findIngredient.getName()).isEqualTo("당근");
        assertThat(findIngredient.getCapacity()).isEqualTo(30);
    }

    @Test
    void 식재료_수정() {
        Long id = ingredientService.registerIngredient("당근", LocalDate.now(), 30,
                "개", "냉장", 1L,"ehgus5825@naver.com");

        ingredientService.modifyIngredient(id, LocalDate.of(2023, 3,24),
                40, "냉동");

        Ingredient findIngredient = ingredientAdapter.getIngredientById(id);
        assertThat(findIngredient.getName()).isEqualTo("당근");
        assertThat(findIngredient.getCapacity()).isEqualTo(40);
        assertThat(findIngredient.getExpirationDate()).isEqualTo(LocalDate.of(2023, 3,24));
        assertThat(findIngredient.getStorageMethod()).isEqualTo("냉동");
    }

    @Test
    void 식재료_삭제() {
        Long id = ingredientService.registerIngredient("당근", LocalDate.now(), 30,
                "개", "냉장", 1L, "ehgus5825@naver.com");

        ingredientService.removeIngredient(id);

        Ingredient findIngredient = ingredientAdapter.getIngredientById(id);
        assertThat(findIngredient.isDeleted()).isTrue();
    }

    @Test
    void 식재료_일괄_삭제() {
        List<Long> ids = new ArrayList<>();
        ids.add(ingredientService.registerIngredient("당근", LocalDate.of(2022, 10, 1),
                10, "개", "냉장", 1L,"ehgus5825@naver.com"));
        ids.add(ingredientService.registerIngredient("고구마", LocalDate.of(2021, 12, 2),
                20, "개", "실온", 1L,"ehgus5825@naver.com"));
        ids.add(ingredientService.registerIngredient("토란", LocalDate.of(2022, 8, 3),
                30, "개", "실온", 1L,"ehgus5825@naver.com"));
        ids.add(ingredientService.registerIngredient("감자", LocalDate.of(2023, 7, 4),
                40, "개", "실온", 1L,"ehgus5825@naver.com"));
        ids.add(ingredientService.registerIngredient("치즈", LocalDate.of(2022, 6, 5),
                50, "장", "냉장", 1L,"ehgus5825@naver.com"));
        ids.add(ingredientService.registerIngredient("쌀", LocalDate.of(2020, 5, 6),
                60, "g", "실온", 1L,"ehgus5825@naver.com"));
        ids.add(ingredientService.registerIngredient("돼지고기", LocalDate.of(2023, 4, 7),
                70, "g", "냉동", 1L,"ehgus5825@naver.com"));

        ingredientService.removeAllIngredients(ids);

        for (Long id : ids) {
            assertThat(ingredientAdapter.getIngredientById(id).isDeleted()).isTrue();
        }
    }

    @Test
    void 식재료_요청 () {
        ingredientService.proposeIngredient("사탕", "g", "ehgus5825@naver.com");
        ingredientService.proposeIngredient("사탕", "개", "ehgus5825@naver.com");
        ingredientService.proposeIngredient("사탕", "ml", "ehgus5825@naver.com");
        ingredientService.proposeIngredient("초콜릿", "g", "ehgus5825@naver.com");
        ingredientService.proposeIngredient("초콜릿", "개", "ehgus5825@naver.com");

        List<SuggestedIngredient> list = ingredientRepository.findSuggestedIngredientList();

        for (SuggestedIngredient ingredient : list) {
            // log.info(ingredient.toString());
            assertThat(ingredient.getId()).isNotNull();
            assertThat(ingredient.getName()).isNotNull();
            assertThat(ingredient.getUnit()).isNotNull();
        }
    }
}