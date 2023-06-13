package refrigerator.back.recipe_recommend.adapter.in.dto;

import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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


    public InRecipeRecommendDTO calculateMatchRate(Double match){
        this.match = match;
        return this;
    }

    @Override
    public void generateImageUrl(ImageGenerator generator) {
        this.image = generator.getUrl(image);
    }
}
