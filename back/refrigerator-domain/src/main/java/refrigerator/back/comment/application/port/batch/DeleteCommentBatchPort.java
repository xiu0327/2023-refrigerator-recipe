package refrigerator.back.comment.application.port.batch;

import refrigerator.back.comment.application.domain.CommentHeartPeople;

import java.util.List;

public interface DeleteCommentBatchPort {

    Long deleteCommentHeart(List<Long> ids);

    Long deleteComment();

    void deleteCommentHeartPeople(CommentHeartPeople commentHeartPeople);
}
