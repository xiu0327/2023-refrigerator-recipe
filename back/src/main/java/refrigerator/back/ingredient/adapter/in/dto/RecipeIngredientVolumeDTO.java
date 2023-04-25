package refrigerator.back.ingredient.adapter.in.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientVolumeDTO {
    private String name;
    private Double volume;
    private String unit;
}
