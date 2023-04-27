package refrigerator.back.myscore.adapter.in.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class InMyScoreDTO {
    private Long scoreId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
    private Double scoreAvg;
    private Integer views;
}