package refrigerator.back.ingredient.adapter.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.config.QuerydslConfig;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QuerydslConfig.class, IngredientUpdateQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IngredientUpdateQueryRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    IngredientUpdateQueryRepository ingredientUpdateQueryRepository;

    @Test
    @DisplayName("식재료 삭제 상태 변경 테스트")
    void updateIngredientDeleteStateTrueTest() {

        Ingredient ingredient = Ingredient.builder()
                .name("감자")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .email("email123@gmail.com")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .build();

        Long id = em.persistAndGetId(ingredient, Long.class);

        Long count = ingredientUpdateQueryRepository.updateIngredientDeleteStateTrue(id);

        assertThat(count).isEqualTo(1);
        assertThat(em.find(Ingredient.class, id).isDeleted())
                .isTrue();
    }

    @Test
    @DisplayName("식재료 삭제 상태 일괄 변경 테스트")
    void updateAllIngredientDeleteStateTrueTest() {

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .email("email123@gmail.com")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        Ingredient ingredient1 = builder.name("감자").build();
        Ingredient ingredient2 = builder.name("고구마").build();

        List<Long> ids = new ArrayList<>();

        ids.add(em.persistAndGetId(ingredient1, Long.class));
        ids.add(em.persistAndGetId(ingredient2, Long.class));

        Long count = ingredientUpdateQueryRepository.updateAllIngredientDeleteStateTrue(ids);
        assertThat(count).isEqualTo(2);
        assertThat(em.find(Ingredient.class, ids.get(0)).isDeleted()).isTrue();
        assertThat(em.find(Ingredient.class, ids.get(1)).isDeleted()).isTrue();
    }

    @Test
    @DisplayName("deleted 상태인 식재료 데이터 모두 삭제")
    void deleteIngredientsTest() {

        Ingredient ingredient = Ingredient.create(
                "감자",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 1),
                30.0,
                "g",
                IngredientStorageType.FREEZER,
                1,
                "email123@gmail.com");

        ingredient.delete();

        Long id = em.persistAndGetId(ingredient, Long.class);

        ingredientUpdateQueryRepository.deleteIngredients();

        assertThat(em.find(Ingredient.class, id))
                .isNull();
    }
}
