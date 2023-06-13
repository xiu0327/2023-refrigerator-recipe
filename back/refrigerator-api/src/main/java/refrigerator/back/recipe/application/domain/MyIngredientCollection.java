package refrigerator.back.recipe.application.domain;

import java.util.Map;

/**
 * 소유 식재료 여부를 판단하는 일급 컬렉션
 * key = 식재료 이름
 * value = 식재료 용량
 */
public class MyIngredientCollection {

    private final Map<String, Double> myIngredients;

    public MyIngredientCollection(Map<String, Double> myIngredients) {
        this.myIngredients = myIngredients;
    }

    public boolean checkOwnedIngredient(String recipeIngredientName, Double recipeIngredientVolume){
        Double myIngredientVolume = myIngredients.get(recipeIngredientName);
        if (myIngredientVolume != null && myIngredientVolume > 0){
            return myIngredientVolume >= recipeIngredientVolume;
        }
        return false;
    }

}
