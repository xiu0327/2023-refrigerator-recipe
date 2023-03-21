package refrigerator.back.mybookmark.adapter.in.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InBookmarkPreviewDTO {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
}
