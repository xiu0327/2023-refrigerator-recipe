package refrigerator.back.recipe.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long recipeID;
    private String recipeName;
    private String image;
    private double scoreAvg;
}
