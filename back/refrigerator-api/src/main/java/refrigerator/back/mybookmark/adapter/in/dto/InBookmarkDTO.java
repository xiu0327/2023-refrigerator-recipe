package refrigerator.back.mybookmark.adapter.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InBookmarkDTO {
    private Long bookmarkID;
    private Long recipeID;
    private String image;
    private String recipeName;
    private Double scoreAvg;
    private Integer views;
}
