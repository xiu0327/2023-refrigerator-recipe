package refrigerator.back.recipe.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSearchConditionDTO{
    private String recipeType;
    private String recipeFoodType;
    private String difficulty;
    private Double score;

}
