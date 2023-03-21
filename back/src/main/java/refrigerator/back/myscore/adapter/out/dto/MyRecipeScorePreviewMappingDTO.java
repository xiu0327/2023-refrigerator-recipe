package refrigerator.back.myscore.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MyRecipeScorePreviewMappingDTO {
    private Long scoreID;
    private Long recipeID;
    private String recipeName;
    private String recipeImage;

    @QueryProjection
    public MyRecipeScorePreviewMappingDTO(Long scoreID, Long recipeID, String recipeName, String recipeImage) {
        this.scoreID = scoreID;
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
    }
}
