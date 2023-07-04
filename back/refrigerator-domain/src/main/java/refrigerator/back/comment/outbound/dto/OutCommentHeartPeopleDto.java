package refrigerator.back.comment.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutCommentHeartPeopleDto {

    protected Long commentId;
    protected Long peopleId;

    @QueryProjection
    public OutCommentHeartPeopleDto(Long commentId, Long peopleId) {
        this.commentId = commentId;
        this.peopleId = peopleId;
    }
}
