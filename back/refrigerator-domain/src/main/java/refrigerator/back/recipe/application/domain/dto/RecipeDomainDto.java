package refrigerator.back.recipe.application.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Field;

@Getter
@Builder
public class RecipeDomainDto {
    Long recipeID;
    String recipeName;
    String image;
    Double scoreAvg;
    String description;
    Integer cookingTime;
    Integer kcal;
    Integer servings;
    String difficulty;

    public boolean checkNotNull(){
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
}
