package refrigerator.back.recipe_search.application.dto;

import lombok.*;

import java.lang.reflect.Field;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeSearchDto {
    private Long recipeID;
    private String recipeName;
    private String image;
    private Double scoreAvg;
    private Integer views;

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
