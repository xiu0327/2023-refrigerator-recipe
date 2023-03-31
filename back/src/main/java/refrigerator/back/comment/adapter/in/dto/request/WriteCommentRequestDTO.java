package refrigerator.back.comment.adapter.in.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WriteCommentRequestDTO {
    private Long recipeId;
    private String content;
}
