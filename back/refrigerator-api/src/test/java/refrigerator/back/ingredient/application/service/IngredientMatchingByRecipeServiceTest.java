package refrigerator.back.ingredient.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.port.in.MatchIngredientByRecipeUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class IngredientMatchingByRecipeServiceTest {

    @Autowired RegisterIngredientUseCase registerIngredientUseCase;
    @Autowired MatchIngredientByRecipeUseCase matchIngredientByRecipeUseCase;
    @Autowired TestData testData;
    @Autowired EntityManager entityManager;

    @Test
    @DisplayName("현재 소유하고 있는 레시피 재료 Id를 반환하는지 테스트")
    void getData() {
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        registerIngredientUseCase.registerIngredient(
                "안심",
                LocalDate.of(2023, 12, 31),
                60.0,
                "g",
                IngredientStorageType.FREEZER,
                2,
                memberId);
        Optional<Long> result = Optional.ofNullable(matchIngredientByRecipeUseCase.getData(memberId, recipeId).get(0));
        if (result.isPresent()){
            Optional<RecipeIngredient> ingredient = entityManager.createQuery("select i from RecipeIngredient i where i.ingredientID= :id", RecipeIngredient.class)
                    .setParameter("id", result.get())
                    .getResultList()
                    .stream().findAny();
            ingredient.ifPresent(item -> Assertions.assertThat(item.getRecipeID()).isEqualTo(recipeId));
        }
    }

    @Test
    @DisplayName("현재 소유하고 있는 레시피 식재료가 없을 때, 빈 배열을 반환하는지 확인")
    void getEmptyData() {
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        List<Long> result = matchIngredientByRecipeUseCase.getData(memberId, recipeId);
        Assertions.assertThat(result.size()).isEqualTo(0);
    }
}