package refrigerator.back.comment.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCommentRequestDTO {
    private Long commentID;
    private String content;
}
