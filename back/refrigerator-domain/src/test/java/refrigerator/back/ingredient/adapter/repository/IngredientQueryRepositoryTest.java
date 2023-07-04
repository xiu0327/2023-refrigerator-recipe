package refrigerator.back.ingredient.adapter.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.global.config.QuerydslConfig;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentDate;
import refrigerator.back.global.time.TestCurrentDate;
import refrigerator.back.ingredient.adapter.dto.OutIngredientDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientImage;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Import({QuerydslConfig.class, IngredientQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IngredientQueryRepositoryTest {

    @Autowired IngredientQueryRepository ingredientQueryRepository;

    @Autowired TestEntityManager em;

    @Test
    @DisplayName("식재료 삭제 테스트")
    void deleteIngredientTest() {

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

        Long count = ingredientQueryRepository.updateIngredientDeleteStateTrue(id);

        assertThat(count).isEqualTo(1);
        assertThat(em.find(Ingredient.class, id).isDeleted())
                .isTrue();
    }

    @Test
    @DisplayName("식재료 일괄 삭제 테스트")
    void deleteAllIngredientsTest() {

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

        Long count = ingredientQueryRepository.updateAllIngredientDeleteStateTrue(ids);
        assertThat(count).isEqualTo(2);
        assertThat(em.find(Ingredient.class, ids.get(0)).isDeleted()).isTrue();
        assertThat(em.find(Ingredient.class, ids.get(1)).isDeleted()).isTrue();
    }

    @Test
    @DisplayName("식재료 목록 조회 테스트")
    void findIngredientListTest() {

        IngredientSearchCondition condition = IngredientSearchCondition.builder()
                .email("email123@gmail.com")
                .storage(IngredientStorageType.FRIDGE)
                .deadline(false)
                .build();

        CurrentDate currentDate = TestCurrentDate.of(2023, 1,1);

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(LocalDate.of(2023, 1, 1))
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        // 나중에 sql로 넣도록 만들어야함.
        IngredientImage image = IngredientImage.builder()
                .id(1)
                .imageFileName("test.png")
                .typeName("type1")
                .build();

        em.persist(image);

        em.persist(builder.name("감자").expirationDate(LocalDate.of(2023, 1, 2))
                .storageMethod(IngredientStorageType.FREEZER).email("email123@gmail.com").build());

        em.persist(builder.name("고구마").expirationDate(LocalDate.of(2022, 12, 31))
                .storageMethod(IngredientStorageType.FRIDGE).email("email123@gmail.com").build());

        em.persist(builder.name("호박").expirationDate(LocalDate.of(2023, 1, 2))
                .storageMethod(IngredientStorageType.FRIDGE).email("email456@gmail.com").build());

        em.persist(builder.name("수박").expirationDate(LocalDate.of(2023, 1, 2))
                .storageMethod(IngredientStorageType.FRIDGE).email("email123@gmail.com").build());

        List<OutIngredientDTO> list = ingredientQueryRepository.findIngredientList(currentDate.now(), condition, PageRequest.of(1, 5));
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getName()).isEqualTo("수박");

    }

    /*
    @Test
    @DisplayName("임박 식재료 조회 테스트")
    void findIngredientListByDeadlineTest() {

        ingredientQueryRepository.findIngredientListByDeadline()

    }

    @Test
    @DisplayName("이메일에 따른 전체 식재료 조회 테스트")
    void findIngredientListOfAllTest() {

        ingredientQueryRepository.findIngredientListOfAll();

    }

    @Test
    @DisplayName("식재료 단건 조회 테스트")
    void findIngredientTest() {

        ingredientQueryRepository.findIngredient()

    }
     */

    @Test
    @DisplayName("nullSafeBuilder 예외 테스트")
    void nullSafeBuilderTest() {
        assertThatThrownBy(() -> ingredientQueryRepository.nullSafeBuilder(() -> null))
                .isInstanceOf(BusinessException.class);
    }
}