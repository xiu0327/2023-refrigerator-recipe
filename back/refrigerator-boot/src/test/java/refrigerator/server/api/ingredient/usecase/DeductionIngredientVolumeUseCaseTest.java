package refrigerator.server.api.ingredient.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.ServerApplication;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.port.in.ingredient.deducation.DeductionIngredientVolumeUseCase;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 식재료 차감 통합 테스트
 */
@SpringBootTest(classes = ServerApplication.class)
@Transactional
class DeductionIngredientVolumeUseCaseTest {

    @Autowired
    DeductionIngredientVolumeUseCase useCase;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("식재료 정상 차감")
    void deduction() {
        // given
        String memberId = "jktest101@gmail.com";
        Long ingredientId = insertTestData(memberId);
        List<IngredientDeductionDTO> recipeIngredients = getRecipeIngredients();
        // when
        useCase.deduction(memberId, recipeIngredients);
        // then
        Ingredient ingredient = em.find(Ingredient.class, ingredientId);
        assertEquals(30.0, ingredient.getCapacity());
    }

    private List<IngredientDeductionDTO> getRecipeIngredients() {
        List<IngredientDeductionDTO> recipeIngredients = new ArrayList<>();
        IngredientDeductionDTO recipeIngredient = IngredientDeductionDTO.builder()
                .unit("g")
                .name("안심")
                .volume(15.0)
                .build();
        recipeIngredients.add(recipeIngredient);
        return recipeIngredients;
    }

    private Long insertTestData(String memberId){
        Ingredient ingredient = Ingredient.create(
                "안심",
                LocalDate.of(2023, 12, 2),
                LocalDate.of(2023, 8, 5),
                45.0,
                "g",
                IngredientStorageType.FREEZER,
                0,
                memberId);
        em.persist(ingredient);
        return ingredient.getId();
    }
}