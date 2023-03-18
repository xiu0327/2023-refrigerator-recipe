package refrigerator.back.myscore.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class MyRecipeScoreMappingDTO {
    private Long scoreID;
    private String recipeName;
    private String recipeImage;
    private Double score;

    @QueryProjection
    public MyRecipeScoreMappingDTO(Long scoreID, String recipeName, String recipeImage, Double score) {
        this.scoreID = scoreID;
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
        this.score = score;
    }
}
