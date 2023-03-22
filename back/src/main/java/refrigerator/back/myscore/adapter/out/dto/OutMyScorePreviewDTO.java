package refrigerator.back.myscore.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutMyScorePreviewDTO {
    private Long scoreId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;

    @QueryProjection
    public OutMyScorePreviewDTO(Long scoreId, Long recipeId, String recipeImage, String recipeName) {
        this.scoreId = scoreId;
        this.recipeId = recipeId;
        this.recipeImage = recipeImage;
        this.recipeName = recipeName;
    }
}
