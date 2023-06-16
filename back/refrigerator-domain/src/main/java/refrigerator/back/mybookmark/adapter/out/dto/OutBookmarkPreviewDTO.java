package refrigerator.back.mybookmark.adapter.out.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutBookmarkPreviewDTO {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;

    @QueryProjection
    public OutBookmarkPreviewDTO(Long bookmarkId, Long recipeId, String recipeImage, String recipeName) {
        this.bookmarkId = bookmarkId;
        this.recipeId = recipeId;
        this.recipeImage = recipeImage;
        this.recipeName = recipeName;
    }
}
