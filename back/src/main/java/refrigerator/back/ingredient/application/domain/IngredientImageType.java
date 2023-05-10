package refrigerator.back.ingredient.application.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IngredientImageType {

    CEREALS(1, "cereals.png"),
    VEGETABLES(2, "vegetables.png"),
    FRUITS(3, "fruits.png"),
    MEAT(4,"meat.png"),
    FISH(5, "fish.png"),
    DAIRY(6,"dairy.png");

    private Integer id;
    private String url;

    public String getUrl(){
        return " " + url;
    }

    //@JsonCreator
    public static IngredientImageType from(Integer id) {
        for (IngredientImageType tag : IngredientImageType.values()) {
            if (tag.getId() == id) {
                return tag;
            }
        }
        return null;
    }

}
