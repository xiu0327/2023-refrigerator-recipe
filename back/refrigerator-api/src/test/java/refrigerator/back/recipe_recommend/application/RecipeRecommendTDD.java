package refrigerator.back.recipe_recommend.application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;


@Slf4j
public class RecipeRecommendTDD {

    @Test
    @DisplayName("레시피 추천 로직 테스트")
    void recipeRecommendTest(){
        // given - 내가 가진 식재료 이름들
        String[] myIngredientNames = {"사과", "배", "계란"};
        // given - 레시피 식재료 이름들
        Map<Long, RecipeIngredientTuple> recipes = new HashMap<>();
        RecipeIngredientTuple item = new RecipeIngredientTuple();
        recipes.put(1L, item);
        // when - 일치율 계산
        for (Map.Entry<Long, RecipeIngredientTuple> entry : recipes.entrySet()) {
            for (String name : myIngredientNames) {
                entry.getValue().count(name);
            }
        }
        double result = recipes.get(1L).calculateMatchingPercent();
        assertThat(result).isEqualTo(60.0);
    }

    static class RecipeIngredientTuple{
        Set<String> names = new HashSet<>();
        int result = 0;

        public RecipeIngredientTuple() {
            names.addAll(Arrays.asList("사과", "계란", "배", "식빵", "공갈빵"));
        }

        public void count(String name){
            if (names.contains(name)){
                result++;
            }
        }

        public double calculateMatchingPercent(){
            BigDecimal a = BigDecimal.valueOf((double) result / names.size());
            BigDecimal b = new BigDecimal("100");
            return a.multiply(b).doubleValue();
        }
    }
}
