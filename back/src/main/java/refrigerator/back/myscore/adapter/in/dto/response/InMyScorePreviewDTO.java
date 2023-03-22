package refrigerator.back.myscore.adapter.in.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InMyScorePreviewDTO {
    private Long scoreId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
}
