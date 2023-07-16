package refrigerator.back.recipe_recommend.application.domain;

import lombok.RequiredArgsConstructor;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;

import java.util.Map;

@RequiredArgsConstructor
public class RecommendRecipeIngredientMap {

    private final Map<String, RecommendRecipeIngredient> recipeIngredientMap;

    public int getIngredientTypeWeightAmount(){
        return recipeIngredientMap.values().stream()
                .mapToInt(RecommendRecipeIngredient::getIngredientTypeWeight).sum();
    }

    public int getMatchedIngredientTypeWeight(MyIngredientDto myIngredient){
        RecommendRecipeIngredient recipeIngredientDto = recipeIngredientMap.get(myIngredient.getName());
        if (recipeIngredientDto != null){
            return recipeIngredientDto.getMatchedIngredientTypeWeight(myIngredient);
        }
        return 0;
    }

}
