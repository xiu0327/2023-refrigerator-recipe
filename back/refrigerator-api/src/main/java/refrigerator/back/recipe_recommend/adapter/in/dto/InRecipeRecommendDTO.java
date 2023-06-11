package refrigerator.back.recipe_recommend.adapter.in.dto;

import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InRecipeRecommendDTO extends Image {

    @NotNull
    private Long recipeID;

    @NotNull
    @Size(max = 100)
    private String recipeName;

    @NotNull
    @Size(max = 500)
    private String image;

    @Min(0)
    @Max(5)
    @NotNull
    private Double scoreAvg;

    @Min(0)
    @Max(100)
    private Double match;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InRecipeRecommendDTO that = (InRecipeRecommendDTO) o;
        return recipeID.equals(that.recipeID) && recipeName.equals(that.recipeName) && image.equals(that.image) && scoreAvg.equals(that.scoreAvg) && match.equals(that.match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeID, recipeName, image, scoreAvg, match);
    }


    public InRecipeRecommendDTO calculateMatchRate(Double match){
        this.match = match;
        return this;
    }

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
