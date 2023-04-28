package refrigerator.back.notification.adapter.out.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutCommentDetailsDTO {

    private String authorId;
    private Long recipeId;

    @QueryProjection
    public OutCommentDetailsDTO(String authorId, Long recipeId) {
        this.authorId = authorId;
        this.recipeId = recipeId;
    }
}
