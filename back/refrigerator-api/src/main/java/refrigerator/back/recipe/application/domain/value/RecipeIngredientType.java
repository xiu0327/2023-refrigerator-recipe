package refrigerator.back.recipe.application.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RecipeIngredientType {

    MAIN("주재료"),
    SUB("부재료"),
    SEASONING("양념"),
    ;

    private String typeName;

    public static RecipeIngredientType lookup(String name){
        return Arrays.stream(RecipeIngredientType.values())
                .filter(type -> type.getTypeName().equals(name))
                .findAny()
                .orElseThrow(() -> new BusinessException(RecipeExceptionType.WRONG_INGREDIENT_TYPE));
    }
}
