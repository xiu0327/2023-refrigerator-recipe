package refrigerator.back.comment.adapter.in.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InCommentDTO {
    private Long commentID;
    private String nickname;
    private Integer heart;
    private String date;
    private Boolean modifiedState;
    private String content;
    private Boolean isMyComment;
}
