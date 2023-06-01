package refrigerator.back.recipe.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeIngredientDTO {
    private Long ingredientID;
    private String name;
    private String type;
    private String volume;
    private Boolean isOwn;
}
