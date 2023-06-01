package refrigerator.back.mybookmark.adapter.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InBookmarkPreviewDTO {
    private Long bookmarkID;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
}
