package refrigerator.back.myscore.adapter.in.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyRecipeScoreDTO {
    private Long scoreID;
    private String recipeImage;
    private String recipeName;
}
