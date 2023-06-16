package refrigerator.back.recipe.application.domain;

import refrigerator.back.recipe.application.domain.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.domain.dto.RecipeIngredientDto;
import refrigerator.back.recipe.application.mapper.RecipeCourseDataMapper;
import refrigerator.back.recipe.application.mapper.RecipeIngredientDataMapper;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeIngredientAndCourseCollection {

    private final Set<RecipeIngredient> ingredients;
    private final Set<RecipeCourse> courses;


    public RecipeIngredientAndCourseCollection(
            Set<RecipeIngredient> ingredients,
            Set<RecipeCourse> courses) {
        this.ingredients = ingredients;
        this.courses = courses;
    }

    /* 제대로 값이 있는지 확인하는 함수, 주로 테스트에서 사용 */
    public boolean isValid(){
        if (ingredients != null && courses != null){
            return ingredients.stream().allMatch(RecipeIngredient::checkNotNull) &&
                    courses.stream().allMatch(RecipeCourse::checkNotNull);
        }
        return false;
    }

    public List<RecipeCourseDto> mappingCourse(RecipeCourseDataMapper mapper){
        return courses.stream().map(mapper::toInRecipeCourseDto)
                .collect(Collectors.toList());
    }

    public List<RecipeIngredientDto> mappingIngredient(RecipeIngredientDataMapper mapper, MyIngredientCollection myIngredients){
        return ingredients.stream()
                .map(ingredient ->
                        mapper.toInRecipeIngredientDto(ingredient,
                                myIngredients.checkOwnedIngredient(
                                        ingredient.getName(),
                                        ingredient.getTransVolume())))
                .collect(Collectors.toList());
    }
}
