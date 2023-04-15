package refrigerator.back.recipe.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.recipe.adapter.in.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe.application.port.out.FindIngredientNameListByMemberPort;
import refrigerator.back.recipe.application.port.out.FindRecommendRecipeInfoPort;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class RecipeRecommendServiceTest {

    @Autowired RecipeRecommendService recipeRecommendService;
    @Autowired FindRecommendRecipeInfoPort findRecommendRecipeInfoPort;
    @Autowired FindIngredientNameListByMemberPort findIngredientNameListByMemberPort;
    @Autowired EntityManager em;
    @Autowired TestData testData;

    @Test
    @DisplayName("레시피 추천")
    void recommend() {
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        String ingredientName = "콩나물";
        em.persist(Ingredient.create(
                ingredientName,
                LocalDate.now(),
                70,
                "g",
                "보관방식",
                "이미지",
                memberId
        ));
        List<InRecipeRecommendDTO> result = recipeRecommendService.recommend(memberId);
        for (int i = 0 ; i < result.size() ; i++){
            if (!(i == result.size() - 1)){
                Assertions.assertThat(result.get(i).getMatch() >= result.get(i+1).getMatch()).isTrue();
            }
            assertNotNull(result.get(i).getMatch());
            assertNotNull(result.get(i).getRecipeScore());
            assertNotNull(result.get(i).getRecipeId());
            assertNotNull(result.get(i).getRecipeImage());
            assertNotNull(result.get(i).getRecipeName());
        }
    }

    @Test
    @DisplayName("일치율이 높은 상위 10개 레시피 식별자 값 구함")
    void getRecommendRecipeIds() {
        Map<Long, Double> matchPercent = calculationMatchPercent();
        List<Long> recipeIds = recipeRecommendService.getRecommendRecipeIds(matchPercent);
        Assertions.assertThat(recipeIds.size()).isEqualTo(10);
        for (int i = 0 ; i < recipeIds.size() - 1 ; i++){
            Assertions.assertThat(matchPercent.get(recipeIds.get(i)) > 0.0).isTrue();
            Assertions.assertThat(matchPercent.get(recipeIds.get(i + 1)) > 0.0).isTrue();
            Assertions.assertThat(matchPercent.get(recipeIds.get(i)) >= matchPercent.get(recipeIds.get(i+1))).isTrue();
        }
    }

    @Test
    @DisplayName("일치율 계산")
    Map<Long, Double> calculationMatchPercent() {
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        String ingredientName = "콩나물";
        em.persist(Ingredient.create(
                ingredientName,
                LocalDate.now(),
                70,
                "g",
                "보관방식",
                "이미지",
                memberId
        ));
        List<String> ingredientNameListByMember = findIngredientNameListByMemberPort.findIngredientNameListByMember(memberId);
        Map<Long, Set<String>> ingredient = findRecommendRecipeInfoPort.getRecipeIngredientNameList();
        Map<Long, Double> result = recipeRecommendService.calculationMatchPercent(
                ingredient, ingredientNameListByMember
        );
        for (Double value : result.values()) {
            Assertions.assertThat(value).isNotNull();
            Assertions.assertThat(value > 0.0 && value <= 100.0).isTrue();
        }
        return result;
    }
}