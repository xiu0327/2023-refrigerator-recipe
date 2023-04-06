package refrigerator.back.comment.adapter.in.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteCommentRequestDTO {
    private Long recipeId;
    private String content;
}
