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
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class IngredientServiceTest {

    @Autowired IngredientUpdateService ingredientService;
    @Autowired IngredientAdapter ingredientAdapter;
    @Autowired IngredientRepository ingredientRepository;

    @Test
    void 식재료_등록() {

        Long id = setIngredient("당근", LocalDate.now(), 30.0, "개", "냉장", 1, "asd123@naver.com");

        Ingredient findIngredient = ingredientAdapter.getIngredientById(id);

        assertThat(findIngredient.getName()).isEqualTo("당근");
        assertThat(findIngredient.getCapacity()).isEqualTo(30);
    }

    @Test
    void 식재료_수정() {

        Long id = setIngredient("당근", LocalDate.now(), 30.0, "개", "냉장", 1,"asd123@naver.com");

        ingredientService.modifyIngredient(id, LocalDate.of(2023, 3,24),
                40.0, "냉동");

        Ingredient findIngredient = ingredientAdapter.getIngredientById(id);
        assertThat(findIngredient.getName()).isEqualTo("당근");
        assertThat(findIngredient.getCapacity()).isEqualTo(40);
        assertThat(findIngredient.getExpirationDate()).isEqualTo(LocalDate.of(2023, 3,24));
        assertThat(findIngredient.getStorageMethod()).isEqualTo("냉동");
    }

    @Test
    void 식재료_삭제() {

        Long id = setIngredient("당근", LocalDate.now(), 30.0, "개", "냉장", 1, "asd123@naver.com");

        ingredientService.removeIngredient(id);

        Ingredient findIngredient = ingredientAdapter.getIngredientById(id);
        assertThat(findIngredient.isDeleted()).isTrue();
    }

    @Test
    void 식재료_일괄_삭제() {

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("당근", "고구마", "토란", "감자", "치즈", "쌀", "돼지고기"));

        List<Long> ids = setIngredientList(arrayList, LocalDate.of(2023, 10, 1), 100.0, "개", 1, "asd123@naver.com");

        ingredientService.removeAllIngredients(ids);

        for (Long id : ids) {
            assertThat(ingredientAdapter.getIngredientById(id).isDeleted()).isTrue();
        }
    }

    @Test
    void 식재료_요청 () {
        setSuggestedIngredient("사탕", 3, "asd123@naver.com");
        setSuggestedIngredient("초콜릿", 2, "asd123@naver.com");

        List<SuggestedIngredient> list = ingredientRepository.findSuggestedIngredientList();

        for (SuggestedIngredient ingredient : list) {
            // log.info(ingredient.toString());
            assertThat(ingredient.getId()).isNotNull();
            assertThat(ingredient.getName()).isNotNull();
            assertThat(ingredient.getUnit()).isNotNull();
        }
    }

    void setSuggestedIngredient(String name, Integer count, String email) {
        List<String> unit = new ArrayList<>(Arrays.asList("g", "개", "ml", "장"));
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            int j = rand.nextInt(4);
            ingredientService.proposeIngredient(name, unit.get(j), email);
        }
    }

    List<Long> setIngredientList(List<String> names, LocalDate date, Double capacity, String unit, Integer image, String email) {

        List<Long> ids = new ArrayList<>();
        List<String> method = new ArrayList<>(Arrays.asList("냉장", "냉동", "실온", "조미료"));
        Random rand = new Random();

        for (String name : names) {
            int i = rand.nextInt(4);
            ids.add(setIngredient(name, date, capacity, unit, method.get(i), image, email));
        }
        return ids;
    }

    Long setIngredient(String name, LocalDate date, Double capacity, String unit, String method, Integer image, String email) {
        return ingredientService.registerIngredient(name, date, capacity, unit, method, image, email);
    }
}
