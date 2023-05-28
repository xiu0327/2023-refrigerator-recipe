package refrigerator.back.recipe.application.domain;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class RecipeRecommend {

    Set<String> names;
    double matchPercent = 0;

    public RecipeRecommend() {
        this.names = new HashSet<>();
    }

    public void count(Set<String> ingredientNames){
        int matchCnt = Sets.intersection(names, ingredientNames).size();
        BigDecimal a = BigDecimal.valueOf((double) matchCnt / names.size());
        BigDecimal b = new BigDecimal("100");
        this.matchPercent = a.multiply(b).doubleValue();
    }

    public void addNames(String name){
        names.add(name);
    }

    public double getMatchPercent() {
        return Double.parseDouble(String.format("%.2f", matchPercent));
    }
}
