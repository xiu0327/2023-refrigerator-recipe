package refrigerator.back.recipe.application.domain.dto;

import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RecipeDto extends Image {

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

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }
}
