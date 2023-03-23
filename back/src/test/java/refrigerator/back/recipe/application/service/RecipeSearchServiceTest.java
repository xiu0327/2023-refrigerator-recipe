package refrigerator.back.recipe.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;
import refrigerator.back.recipe.application.domain.value.RecipeType;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class RecipeSearchServiceTest {

    @Autowired RecipeSearchService searchService;
    @Autowired EntityManager em;

    @Test
    void 레시피_검색() {
        // given
        String searchWord = "김치";
        String searchRecipeType = RecipeType.KOREA.getName();
        RecipeSearchCondition condition = RecipeSearchCondition.builder()
                .recipeType(searchRecipeType)
                .searchWord(searchWord).build();
        // when
        List<InRecipeDTO> recipes = searchService.search(condition, 0, 15).getData();
        // then
        for (InRecipeDTO recipe : recipes) {
            /* 모든 필드 not null */
            assertNotNull(recipe.getRecipeID());
            assertNotNull(recipe.getRecipeName());
            assertNotNull(recipe.getImage());
            assertNotNull(recipe.getScoreAvg());
            assertNotNull(recipe.getViews());
            /* 레시피 명에 검색어가 포함되어 있는지 */
            log.info(recipe.getRecipeName());
            String findRecipeType = em.find(Recipe.class, recipe.getRecipeID()).getRecipeType();
            assertThat(searchRecipeType).isEqualTo(findRecipeType);
            assertThat(recipe.getRecipeName()).contains(searchWord);
        }
    }

}