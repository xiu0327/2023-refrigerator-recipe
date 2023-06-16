package refrigerator.back.comment.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteCommentRequestDTO {
    private Long recipeId;
    private String content;
}
