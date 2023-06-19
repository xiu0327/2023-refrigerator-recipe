package refrigerator.back.comment.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDTO {
    private Long commentId;
    private String nickname;
    private Integer heart;
    private LocalDateTime createDate;
    private Boolean modifiedState;
    private String content;
    private String memberId;
}
