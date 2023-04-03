package refrigerator.back.comment.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentRequestDTO {
    private Long commentId;
    private String content;
}
