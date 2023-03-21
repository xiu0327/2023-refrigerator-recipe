package refrigerator.back.recipe.application.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum RecipeType {
    SOUTHEAST_ASIA("동남아시아"),
    WESTERN("서양"),
    ITALY("이탈리아"),
    JAPAN("일본"),
    CHINA("중국"),
    FUSSION("퓨전"),
    KOREA("한식"),
    NOT_TYPE("타입 없음")
    ;

    private String name;

    public static List<String> getRecipeTypeList(){
        return Arrays.stream(RecipeType.values())
                .map(RecipeType::getName)
                .collect(Collectors.toList());
    }

    public static RecipeType lookup(String name){
        return Arrays.stream(RecipeType.values())
                .filter(recipeType -> recipeType.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new BusinessException(RecipeExceptionType.NOT_FOUND_RECIPE));
    }
}
