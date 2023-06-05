package refrigerator.back.recipe.application.domain;

import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.Set;

public class IngredientMatchPercent {
    private final Set<String> names;
    private double matchPercent;

    public IngredientMatchPercent(Set<String> names) {
        this.names = names;
        this.matchPercent = 0;
    }

    void count(Set<String> myIngredientNames){
        int matchCnt = Sets.intersection(names, myIngredientNames).size();
        BigDecimal a = BigDecimal.valueOf((double) matchCnt / names.size());
        BigDecimal b = new BigDecimal("100");
        this.matchPercent = a.multiply(b).doubleValue();
    }

    double getMatchPercent() {
        return Double.parseDouble(String.format("%.2f", matchPercent));
    }

}
