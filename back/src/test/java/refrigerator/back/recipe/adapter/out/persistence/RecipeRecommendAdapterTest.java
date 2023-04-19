package refrigerator.back.recipe.adapter.out.persistence;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class RecipeRecommendAdapterTest {

    @Autowired RecipeRecommendAdapter recipeRecommendAdapter;
    @Autowired EntityManager em;
    @Autowired TestData testData;

    @Test
    @DisplayName("추천을 위한 레시피의 이름과 식재료 목록 불어오기")
    void getRecipeNameAndIngredient() {
        Map<Long, Set<String>> result = recipeRecommendAdapter.getRecipeIngredientNameList();
        for (Set<String> value : result.values()) {
            Assertions.assertThat(value).isNotEmpty();
        }
    }

    @Test
    @DisplayName("회원의 식재료 이름 목록 불러오기")
    void findIngredientNameListByMember() {
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
        List<String> result = recipeRecommendAdapter.findIngredientNameListByMember(memberId);
        Assertions.assertThat(ingredientName).contains(result);
    }

    @Test
    @DisplayName("추천 레시피 정보 불러오기")
    void findRecommendRecipeInfo() {
        List<Long> recipeIds = new ArrayList<>();
        for(long i = 0 ; i < 10L ; i++){
            recipeIds.add(i);
        }
        List<InRecipeRecommendDTO> result = recipeRecommendAdapter.findRecommendRecipeInfo(recipeIds);
        for (InRecipeRecommendDTO dto : result) {
            log.info("dto = {}", dto);
        }
    }
}