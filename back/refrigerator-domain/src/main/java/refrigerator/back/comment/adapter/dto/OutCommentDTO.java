package refrigerator.back.comment.adapter.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutCommentDTO {
    private Long commentId;
    private String nickname;
    private Integer heart;
    private LocalDateTime createDate;
    private Boolean modifiedState;
    private String content;
    private String memberId;

    @QueryProjection
    public OutCommentDTO(Long commentId, String nickname, Integer heart, LocalDateTime createDate, Boolean modifiedState, String content, String memberId) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.heart = heart;
        this.createDate = createDate;
        this.modifiedState = modifiedState;
        this.content = content;
        this.memberId = memberId;
    }
}
