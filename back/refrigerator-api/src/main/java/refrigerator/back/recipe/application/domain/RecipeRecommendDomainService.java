package refrigerator.back.recipe.application.domain;

import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;

/**
 * 추천 레시피 추출
 * 1. 레시피 식재료 이름과 사용자 식재료 이름을 비교하여 일치율 도출 -> IngredientMatchRate
 * 2. 상위 일치율 10개의 레시피 식별자 값을 추출
 */

public class RecipeRecommendDomainService {

   private final Map<Long, RecommendMatchPercent> percents;
   private final Set<String> myIngredientNames;

    public RecipeRecommendDomainService(Map<Long, RecommendMatchPercent> percents,
                                        Set<String> myIngredientNames) {
        this.percents = percents;
        this.myIngredientNames = myIngredientNames;
    }

    /* 비즈니스 로직 */

    public List<Long> extractTopTenMatchPercentIds(){
        isValidMyIngredients();
        percents.keySet().forEach(key -> percents.get(key).calculateMatchPercent(myIngredientNames));
        return percents.keySet().stream()
                .sorted(comparingDouble(
                        key -> percents.get(key).getMatchPercent()).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public double findMatchPercentByRecipeId(Long recipeId){
        return percents.get(recipeId).getMatchPercent();
    }

    private void isValidMyIngredients() {
        if (myIngredientNames.size() <= 0){
            throw new BusinessException(RecipeExceptionType.EMPTY_MEMBER_INGREDIENT);
        }
    }

}
