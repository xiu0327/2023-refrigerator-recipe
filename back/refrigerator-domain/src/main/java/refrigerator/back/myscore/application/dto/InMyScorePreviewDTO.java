package refrigerator.back.myscore.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InMyScorePreviewDTO {
    private Long scoreId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
}