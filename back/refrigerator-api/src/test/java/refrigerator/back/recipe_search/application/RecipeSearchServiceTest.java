package refrigerator.back.recipe_search.application;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchDto;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe.application.domain.value.RecipeType;
import refrigerator.back.recipe.exception.RecipeExceptionType;
import refrigerator.back.recipe_search.application.port.in.SearchRecipeUseCase;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RecipeSearchServiceTest {

    @Autowired SearchRecipeUseCase searchRecipeUseCase;

    @Test
    void 레시피_검색() {
        // given
        String searchWord = "김치";
        String searchRecipeType = RecipeType.KOREA.getName();
        RecipeSearchCondition condition = RecipeSearchCondition.builder()
                .recipeType(searchRecipeType)
                .searchWord(searchWord).build();
        // when
        List<InRecipeSearchDto> recipes = searchRecipeUseCase.search(condition, 0, 15);
        // then
        Assertions.assertTrue(recipes.stream().allMatch(InRecipeSearchDto::checkNotNull));
    }

    @Test
    void 레시피_목록() {
        // when
        List<InRecipeSearchDto> recipes = searchRecipeUseCase.normalSearch(0, 15);
        // then
        Assertions.assertTrue(recipes.stream().allMatch(InRecipeSearchDto::checkNotNull));
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
                searchRecipeUseCase.search(condition, 0, 15);
            } catch(BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(RecipeExceptionType.NOT_ACCEPTABLE_RANGE);
                throw e;
            }
        });
    }

}