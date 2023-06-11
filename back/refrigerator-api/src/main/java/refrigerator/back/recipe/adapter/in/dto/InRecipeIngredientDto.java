package refrigerator.back.recipe.adapter.in.dto;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeIngredientDto {
    private Long ingredientID;
    private String name;
    private String type;
    private String volume;
    private String transUnit;
    private String transVolume;
    private Boolean isOwned;
}
