package refrigerator.back.recipe.adapter.out;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.recipe.application.domain.dto.RecipeDomainDto;
import refrigerator.back.recipe.application.domain.MyIngredientCollection;
import refrigerator.back.recipe.application.domain.RecipeIngredientAndCourseCollection;
import refrigerator.back.recipe.application.port.out.GetMyIngredientDataPort;
import refrigerator.back.recipe.application.port.out.GetRecipeBasicsDataPort;
import refrigerator.back.recipe.application.port.out.GetRecipeIngredientAndCourseDataPort;
import refrigerator.configuration.RecipeTestConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(RecipeTestConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeDataPortTest {

    @Autowired GetRecipeBasicsDataPort recipePort;
    @Autowired GetMyIngredientDataPort myIngredientDataPort;
    @Autowired GetRecipeIngredientAndCourseDataPort ingredientAndCourseDataPort;

    @Test
    @DisplayName("레시피 기본 정보 제대로 가져오는지 확인")
    void getRecipeBasics() {
        for (long id = 1L ; id < 10L ; id++) {
            RecipeDomainDto data = recipePort.getData(id);
            assertTrue(data.checkNotNull());
        }
    }

    @Test
    @DisplayName("사용자가 소유한 레시피 제대로 가져오는지 확인")
    void getMyIngredients() {
        MyIngredientCollection result = myIngredientDataPort.getMyIngredients("nhtest@gmail.com");
        assertNotNull(result);
    }

    @Test
    @DisplayName("전체 레시피에 대해 식재료와 과정 데이터를 제대로 가져오는지 확인")
    void getData() {
        for (long id = 1L ; id < 10L ; id++) {
            RecipeIngredientAndCourseCollection result = ingredientAndCourseDataPort.getData(id);
            assertTrue(result.isValid());
        }
    }

}