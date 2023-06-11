package refrigerator.back.recipe_search.adapter.out;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.recipe.application.domain.value.RecipeType;
import refrigerator.configuration.RecipeSearchTestConfiguration;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.application.domain.RecipeSearchDataCollection;
import refrigerator.back.recipe_search.application.port.out.GetRecipeSearchDataPort;
import refrigerator.back.recipe_search.application.port.out.GetSearchConditionDataPort;

@DataJpaTest
@Import(RecipeSearchTestConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeSearchDataPortTest {

    @Autowired GetSearchConditionDataPort getSearchConditionDataPort;
    @Autowired GetRecipeSearchDataPort getRecipeSearchDataPort;

    @Test
    void search() {
        // given
        String searchWord = "김치";
        String searchRecipeType = RecipeType.KOREA.getName();
        RecipeSearchCondition condition = RecipeSearchCondition.builder()
                .recipeType(searchRecipeType)
                .searchWord(searchWord).build();
        // when
        RecipeSearchDataCollection result = getRecipeSearchDataPort.search(condition, 0, 11);
        Assertions.assertTrue(result.isValid());
    }

    @Test
    void normalSearch() {
        // TODO : 정렬 조건 확인
        RecipeSearchDataCollection result = getRecipeSearchDataPort.normalSearch(0, 11);
        Assertions.assertTrue(result.isValid());
    }
}