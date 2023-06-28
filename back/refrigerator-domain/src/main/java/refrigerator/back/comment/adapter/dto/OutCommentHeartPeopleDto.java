package refrigerator.back.comment.adapter.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutCommentHeartPeopleDto {

    private Long commentId;
    private Long peopleId;

    @QueryProjection
    public OutCommentHeartPeopleDto(Long commentId, Long peopleId) {
        this.commentId = commentId;
        this.peopleId = peopleId;
    }
}
