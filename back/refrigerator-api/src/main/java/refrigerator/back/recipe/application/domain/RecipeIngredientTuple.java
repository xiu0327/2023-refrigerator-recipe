package refrigerator.back.recipe.application.domain;

import lombok.Getter;

@Getter
public class RecipeIngredientTuple {
    private String name;
    private String type;

    public RecipeIngredientTuple(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getWeightByType(){
        if(type.equals("주재료")) return 3;
        if(type.equals("부재료")) return 2;
        return 1;
    }
}
