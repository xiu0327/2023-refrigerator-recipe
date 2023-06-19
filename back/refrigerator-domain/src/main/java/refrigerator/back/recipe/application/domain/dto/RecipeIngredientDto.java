package refrigerator.back.recipe.application.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto {
    private Long ingredientID;
    private String name;
    private String type;
    private String volume;
    private String transUnit;
    private String transVolume;
    private Boolean isOwned;
}
