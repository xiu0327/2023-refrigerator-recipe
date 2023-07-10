package refrigerator.back.mybookmark.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkDto {
    private Long bookmarkID;
    private Long recipeId;
    private String image;
    private String recipeName;
    private Double scoreAvg;
    private Integer views;
}
