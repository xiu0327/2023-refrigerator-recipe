package refrigerator.back.recipe.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

import java.lang.reflect.Field;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class InRecipeDto extends Image {

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
    List<InRecipeIngredientDto> ingredients;
    List<InRecipeCourseDto> courses;

    @JsonIgnore
    public boolean isNotNull(){
        try{
            for (Field field : getClass().getDeclaredFields()){
                if (field.get(this) == null){
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }
}
