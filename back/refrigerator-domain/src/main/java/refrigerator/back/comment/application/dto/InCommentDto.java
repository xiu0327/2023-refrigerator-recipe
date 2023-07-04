package refrigerator.back.comment.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class InCommentDto {
    private Long commentId;
    private String nickname;
    private Integer heart;
    private String date;
    private Boolean modifiedState;
    private String content;
    private InCommentHeartPeopleDto likedPeopleInfo;
}
