package refrigerator.back.recipe.application.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RecipeDto {

    Long recipeID;
    String recipeName;
    String image;
    Double scoreAvg;
    String description;
    String cookingTime;
    String kcal;
    String servings;
    String difficulty;
    Boolean isBookmarked;
    List<RecipeIngredientDto> ingredients;
    List<RecipeCourseDto> courses;
}
