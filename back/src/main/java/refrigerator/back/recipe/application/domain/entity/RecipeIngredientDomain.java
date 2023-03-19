package refrigerator.back.recipe.application.domain.entity;

import lombok.Builder;
import lombok.Getter;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;

@Getter
@Builder
public class RecipeIngredientDomain {
    private Long ingredientID;
    private String name;
    private String volume;
    private Double transVolume;
    private String transUnit;
    private RecipeIngredientType type;
}
