package refrigerator.back.comment.adapter.in.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class InCommentDTO {
    private Long commentId;
    private String nickname;
    private Integer heart;
    private LocalDate createDate;
    private Boolean modifiedState;
    private String content;
}
