package refrigerator.back.recipe.adapter.in.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class InRecipeRecommendDTO {
    private Long recipeId;
    private String recipeName;
    private String recipeImage;
    private Double recipeScore;
    private Double match;

    public void setMatch(Double match) {
        this.match = Double.valueOf(String.format("%.2f", match));
    }
}
