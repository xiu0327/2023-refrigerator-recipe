package refrigerator.back.recipe.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.in.dto.*;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.domain.entity.RecipeViews;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecipeServiceTest {

    @Autowired RecipeService recipeService;
    @Autowired EntityManager em;

    @Test
    void 레시피_상세_조회() {
        // given
        Long recipeId = 1L;
        int before = em.find(RecipeViews.class, recipeId).getViews();
        // when
        Recipe recipe = recipeService.getRecipe(recipeId, false);
        // then
        /* 모든 필드 not null */
        int after = em.find(RecipeViews.class, recipeId).getViews();
        assertThat(recipe.getIngredients()).isNotEmpty();
        /* 최초 조회이기 때문에 조회수 증가 */
        assertThat(after).isEqualTo(before + 1);
    }

    @Test
    void 레시피_상세_재조회() {
        // given
        Long recipeId = 1L;
        int before = em.find(RecipeViews.class, recipeId).getViews();
        // when
        Recipe recipe = recipeService.getRecipe(recipeId, true);
        // then
        int after = em.find(RecipeViews.class, recipeId).getViews();
        assertThat(recipe.getIngredients()).isNotEmpty();
        /* 재조회이기 때문에 조회수 증가 X */
        assertThat(after).isEqualTo(before);
    }

    @Test
    void 레시피_목록_조회() {
        // when
        List<InRecipeDTO> recipes = recipeService.getRecipeList(0, 11).getData();
        // then
        /* 모든 필드 not null */
        for (InRecipeDTO recipe : recipes) {
            assertNotNull(recipe.getRecipeID());
            assertNotNull(recipe.getRecipeName());
            assertNotNull(recipe.getImage());
            assertNotNull(recipe.getScoreAvg());
            assertNotNull(recipe.getViews());
        }
        assertThat(recipes.size()).isEqualTo(11);
    }

    @Test
    void 레시피_과정_조회() {
        Long recipeId = 1L;
        List<InRecipeCourseDTO> courses = recipeService.getRecipeCourse(recipeId).getData();
        /* 모든 필드 not null */
        for (InRecipeCourseDTO course : courses) {
            assertNotNull(course.getStep());
            assertNotNull(course.getExplanation());
            assertNotNull(course.getImage());
        }
    }

    private void notNullTest(InRecipeDetailDTO recipe) {
        assertNotNull(recipe.getRecipeID());
        assertNotNull(recipe.getRecipeName());
        assertNotNull(recipe.getDescription());
        assertNotNull(recipe.getCookingTime());
        assertNotNull(recipe.getKcal());
        assertNotNull(recipe.getServings());
        assertNotNull(recipe.getDifficulty());
        assertNotNull(recipe.getImage());
        assertNotNull(recipe.getScoreAvg());
        assertNotNull(recipe.getViews());
        assertNotNull(recipe.getBookmarks());
        assertNotNull(recipe.getRecipeFoodTypeName());
        assertNotNull(recipe.getRecipeCategoryName());
        for (InRecipeIngredientDTO ingredient : recipe.getIngredients()) {
            assertNotNull(ingredient.getIngredientID());
            assertNotNull(ingredient.getName());
            assertNotNull(ingredient.getType());
            assertNotNull(ingredient.getVolume());
        }
    }
}