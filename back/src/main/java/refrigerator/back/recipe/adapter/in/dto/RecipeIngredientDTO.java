package refrigerator.back.recipe.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO {
    private Long ingredientID;
    private String name;
    private String type;
    private String volume;
}
