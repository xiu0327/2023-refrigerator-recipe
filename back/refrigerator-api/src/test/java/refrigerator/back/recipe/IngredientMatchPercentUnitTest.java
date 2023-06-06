package refrigerator.back.recipe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.recipe.application.domain.RecommendMatchPercent;
import refrigerator.back.recipe.application.domain.RecipeIngredientTuple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class IngredientMatchPercentUnitTest {

    @Test
    @DisplayName("일치율 계산")
    void calculateMatchPercent(){
        RecommendMatchPercent matchPercent = new RecommendMatchPercent(settingIngredientData());
        Set<String> myIngredients = new HashSet<>(Arrays.asList("사과", "계란"));
        matchPercent.calculateMatchPercent(myIngredients);
        Assertions.assertThat(matchPercent.getMatchPercent()).isEqualTo(62.5);
    }

    private Set<RecipeIngredientTuple> settingIngredientData() {
        Set<RecipeIngredientTuple> data = new HashSet<>();
        data.add(new RecipeIngredientTuple("사과", "주재료"));
        data.add(new RecipeIngredientTuple("계란", "부재료"));
        data.add(new RecipeIngredientTuple("케찹", "양념"));
        data.add(new RecipeIngredientTuple("소금", "양념"));
        data.add(new RecipeIngredientTuple("후추", "양념"));
        return data;
    }

}