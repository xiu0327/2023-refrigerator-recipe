package refrigerator.back.recipe_recommend.application.dto;

import lombok.*;
import refrigerator.back.global.image.Image;
import refrigerator.back.global.image.ImageGenerator;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InRecipeRecommendDTO extends Image {

    private Long recipeID;

    private String recipeName;

    private String image;

    private Double scoreAvg;

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
