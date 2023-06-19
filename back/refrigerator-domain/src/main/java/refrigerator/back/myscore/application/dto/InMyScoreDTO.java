package refrigerator.back.myscore.application.dto;

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