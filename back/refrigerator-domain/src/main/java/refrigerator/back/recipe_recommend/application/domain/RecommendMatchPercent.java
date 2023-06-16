package refrigerator.back.recipe_recommend.application.domain;

import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RecommendMatchPercent {
    private final Set<RecipeIngredientTuple> recipeIngredients;
    private double matchPercent;

    public RecommendMatchPercent(Set<RecipeIngredientTuple> names) {
        this.recipeIngredients = names;
        this.matchPercent = 0;
    }

    public void calculateMatchPercent(Set<String> myIngredientNames){
        Map<String, Integer> typeScores = new HashMap<>();
        recipeIngredients.forEach(ingredientTuple ->
                typeScores.put(ingredientTuple.getName(), ingredientTuple.getWeightByType()));
        int amount = recipeIngredients.stream()
                .mapToInt(RecipeIngredientTuple::getWeightByType)
                .sum();
        int matchAmount = Sets.intersection(typeScores.keySet(), myIngredientNames).stream()
                .mapToInt(typeScores::get)
                .sum();
        BigDecimal a = BigDecimal.valueOf((double) matchAmount / amount);
        BigDecimal b = new BigDecimal(100);
        this.matchPercent = a.multiply(b).doubleValue();
    }

    public double getMatchPercent() {
        return Double.parseDouble(String.format("%.2f", matchPercent));
    }

}
