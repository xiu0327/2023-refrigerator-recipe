package refrigerator.back.ingredient.adapter.repository;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.config.QuerydslConfig;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, IngredientQuerySubRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IngredientQuerySubRepositoryTest {

    @Autowired IngredientQuerySubRepository ingredientQuerySubRepository;

    @Autowired TestEntityManager em;

    @Test
    @DisplayName("등록된 식재료 목록 조회")
    void getIngredientListTest() {
        // TODO : 기존에 저장된 데이터가 있어야함.
    }


    @Test
    @DisplayName("요청된 식재료 저장 테스트")
    void saveSuggestIngredientTest() {
        SuggestedIngredient ingredient = SuggestedIngredient.builder()
                .name("감자")
                .email("email123@gmail.com")
                .unit("g")
                .build();

        Long id = ingredientQuerySubRepository.saveSuggestIngredient(ingredient);

        assertThat(em.find(SuggestedIngredient.class, id))
                .isEqualTo(ingredient);

    }

    @Test
    @DisplayName("레시피 ID에 맞는 레시피 식재료 목록 조회")
    void getRecipeIngredientTest() {
        // TODO : 기존에 저장된 데이터가 있어야함.
    }

}