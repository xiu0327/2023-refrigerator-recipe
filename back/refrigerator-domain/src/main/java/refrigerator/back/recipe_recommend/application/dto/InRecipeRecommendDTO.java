package refrigerator.back.recipe_recommend.application.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InRecipeRecommendDTO {

    private Long recipeID;

    private String recipeName;

    private String image;

    private Double scoreAvg;

    private Double match;

    public InRecipeRecommendDTO calculateMatchRate(Double match){
        this.match = match;
        return this;
    }

}
