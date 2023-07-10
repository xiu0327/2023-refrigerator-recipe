package refrigerator.back.mybookmark.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkPreviewDto {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
}
