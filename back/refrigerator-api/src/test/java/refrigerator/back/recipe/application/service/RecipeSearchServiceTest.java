package refrigerator.back.recipe.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDTO;
import refrigerator.back.recipe.application.domain.entity.Recipe;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;
import refrigerator.back.recipe.application.domain.entity.RecipeSearchCondition;
import refrigerator.back.recipe.application.domain.value.RecipeType;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

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
        List<InRecipeDTO> recipes = searchService.search(condition, 0, 15);
        List<Recipe> findRecipes = em.createQuery("select r from Recipe r join fetch r.ingredients where r.recipeID in (:ids)", Recipe.class)
                .setParameter("ids", recipes.stream()
                        .map(InRecipeDTO::getRecipeID)
                        .collect(Collectors.toList()))
                .getResultList();
        // then
        for (Recipe recipe : findRecipes) {
            /* 검색 파라미터로 입력한 레시피 타입이 맞는지 */
            assertThat(searchRecipeType).isEqualTo(recipe.getRecipeType());
            /* 레시피 명 또는 식재료 목록에 에 검색어가 포함되었는지 */
            List<String> ingredientNames = recipe.getIngredients().stream()
                    .map(RecipeIngredient::getName)
                    .collect(Collectors.toList());
            assertThat(
                    recipe.getRecipeName().contains(searchWord) ||
                            ingredientNames.contains(searchWord)
            ).isTrue();
        }
    }

    @Test
    void 레시피_검색_실패_별점_범위() {
        /* 허용하지 않는 별점의 범위를 입력했을 때, 에러 발생 */
        // given
        String searchRecipeType = RecipeType.KOREA.getName();
        RecipeSearchCondition condition = RecipeSearchCondition.builder()
                .recipeType(searchRecipeType)
                .score(6.2)
                .searchWord("김치").build();
        // when
        Assertions.assertThrows(BusinessException.class, () -> {
            try{
                searchService.search(condition, 0, 15);
            } catch(BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(RecipeExceptionType.NOT_ACCEPTABLE_RANGE);
                throw e;
            }
        });
    }

}