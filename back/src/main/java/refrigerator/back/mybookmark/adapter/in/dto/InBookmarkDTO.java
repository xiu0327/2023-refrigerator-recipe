package refrigerator.back.mybookmark.adapter.in.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class InBookmarkDTO {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
    private Double scoreAvg;
    private Integer views;
}