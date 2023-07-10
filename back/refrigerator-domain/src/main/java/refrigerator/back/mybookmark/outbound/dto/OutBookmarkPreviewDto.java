package refrigerator.back.mybookmark.outbound.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutBookmarkPreviewDto {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImageName;
    private String recipeName;
    private LocalDateTime createDateTime;

    @QueryProjection
    public OutBookmarkPreviewDto(Long bookmarkId, Long recipeId, String recipeImageName, String recipeName, LocalDateTime createDateTime) {
        this.bookmarkId = bookmarkId;
        this.recipeId = recipeId;
        this.recipeImageName = recipeImageName;
        this.recipeName = recipeName;
        this.createDateTime = createDateTime;
    }
}
