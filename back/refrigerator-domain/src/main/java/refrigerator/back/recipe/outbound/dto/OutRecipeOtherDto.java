package refrigerator.back.recipe.outbound.dto;

import lombok.Getter;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;

import java.io.Serializable;
import java.util.Set;

@Getter
public class OutRecipeOtherDto implements Serializable {
    Set<RecipeIngredient> ingredients;
    Set<RecipeCourse> courses;

    public OutRecipeOtherDto(Set<RecipeIngredient> ingredients, Set<RecipeCourse> courses) {
        this.ingredients = ingredients;
        this.courses = courses;
    }
}
