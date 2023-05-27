package refrigerator.back.recipe.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeIngredientVolumeDTO {
    private Long recipeIngredientId;
    private String name;
    private Double volume;
    private String unit;
    private String type;
}
