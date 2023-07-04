package refrigerator.back.comment.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refrigerator.back.comment.application.mapper.CommentMapper;
import refrigerator.back.comment.application.service.CommentTimeService;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class CommentDto {
    private Long commentId;
    private String nickname;
    private Integer heart;
    private LocalDateTime createDate;
    private Boolean modifiedState;
    private String content;
    private String memberId;

    public InCommentDto mapping(CommentMapper mapper,
                                CommentTimeService timeService,
                                Object likedPeopleId){
        return mapper.toInCommentDto(this, timeService.replace(createDate), likedPeopleId);
    }
}
