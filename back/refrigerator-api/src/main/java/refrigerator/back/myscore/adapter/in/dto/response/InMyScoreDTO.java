package refrigerator.back.myscore.adapter.in.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InMyScoreDTO {
    private Long scoreID;
    private Long recipeID;
    private String image;
    private String recipeName;
    private Double scoreAvg;
    private Integer views;
}