package refrigerator.back.ingredient.adapter.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import refrigerator.back.global.config.QuerydslConfig;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IngredientPersistenceRepositoryTest {

    @Autowired IngredientPersistenceRepository ingredientPersistenceRepository;

    @Autowired TestEntityManager em;

    @Test
    @DisplayName("식재료 추가 테스트")
    void addIngredientTest() {

        Ingredient ingredient = Ingredient.create(
                "감자",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 1),
                30.0,
                "g",
                IngredientStorageType.FREEZER,
                1,
                "email123@gmail.com");

        Ingredient saveIngredient = ingredientPersistenceRepository.save(ingredient);

        assertThat(saveIngredient.getName()).isEqualTo("감자");
        assertThat(saveIngredient.getRegistrationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(saveIngredient.getExpirationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(saveIngredient.getCapacity()).isEqualTo(30.0);
        assertThat(saveIngredient.getCapacityUnit()).isEqualTo("g");
        assertThat(saveIngredient.getStorageMethod()).isEqualTo(IngredientStorageType.FREEZER);
        assertThat(saveIngredient.getImage()).isEqualTo(1);
        assertThat(saveIngredient.getEmail()).isEqualTo("email123@gmail.com");
    }

    @Test
    @DisplayName("이메일이 일치하는 식재료 리스트 조회 : 삭제 X")
    void findByEmailAndDeletedFalseTest() {
        String email = "email123@gmail.com";

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .email(email)
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        Ingredient ingredient1 = builder.name("감자").build();
        Ingredient ingredient2 = builder.name("고구마").build();

        em.persist(ingredient1);
        em.persist(ingredient2);

        assertThat(ingredientPersistenceRepository.findByEmailAndDeletedFalse(email).size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("이메일이 일치하는 식재료 리스트 조회 : 삭제 O")
    void findByEmailAndDeletedTrueTest() {
        String email = "email123@gmail.com";

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .expirationDate(LocalDate.of(2023, 1, 1))
                .registrationDate(LocalDate.of(2023, 1, 1))
                .email(email)
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FREEZER)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        Ingredient ingredient1 = builder.name("감자").build();
        Ingredient ingredient2 = builder.name("고구마").build();

        em.persist(ingredient1);
        em.persist(ingredient2);

        ingredient1.delete();

        List<Ingredient> ingredients = ingredientPersistenceRepository.findByEmailAndDeletedFalse(email);

        assertThat(ingredients.size()).isEqualTo(1);
        assertThat(ingredients.get(0)).isEqualTo(ingredient2);
    }

    @Test
    @DisplayName("id가 일치하는 식재료 리스트 조회 : 삭제 X")
    void findByIdAndDeletedFalseTest() {
        Ingredient ingredient = Ingredient.create(
                "감자",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 1),
                30.0,
                "g",
                IngredientStorageType.FREEZER,
                1,
                "email123@gmail.com");

        Long id = em.persistAndGetId(ingredient, Long.class);

        assertThat(ingredientPersistenceRepository.findByIdAndDeletedFalse(id).orElse(null))
                .isEqualTo(ingredient);
    }

    @Test
    @DisplayName("id가 일치하는 식재료 리스트 조회 : 삭제 O")
    void findByIdAndDeletedTrueTest() {
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

        assertThat(ingredientPersistenceRepository.findByIdAndDeletedFalse(id).orElse(null))
                .isNull();
    }

}