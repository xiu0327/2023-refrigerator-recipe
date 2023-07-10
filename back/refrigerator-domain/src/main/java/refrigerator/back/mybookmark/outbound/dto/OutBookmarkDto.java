package refrigerator.back.mybookmark.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutBookmarkDto {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImageName;
    private String recipeName;
    private Double score;
    private Integer views;
    private LocalDateTime createDateTime;

    @QueryProjection
    public OutBookmarkDto(Long bookmarkId, Long recipeId, String recipeImageName, String recipeName, Double score, Integer views, LocalDateTime createDateTime) {
        this.bookmarkId = bookmarkId;
        this.recipeId = recipeId;
        this.recipeImageName = recipeImageName;
        this.recipeName = recipeName;
        this.score = score;
        this.views = views;
        this.createDateTime = createDateTime;
    }
}
