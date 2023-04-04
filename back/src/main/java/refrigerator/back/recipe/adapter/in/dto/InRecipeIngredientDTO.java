package refrigerator.back.recipe.adapter.in.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeIngredientDTO {
    private Long ingredientID;
    private String name;
    private String type;
    private String volume;
}
