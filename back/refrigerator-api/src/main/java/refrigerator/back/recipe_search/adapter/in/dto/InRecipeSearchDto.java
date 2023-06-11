package refrigerator.back.recipe_search.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

import java.lang.reflect.Field;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeSearchDto extends Image {
    private Long recipeID;
    private String recipeName;
    private String image;
    private Double scoreAvg;
    private Integer views;

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
