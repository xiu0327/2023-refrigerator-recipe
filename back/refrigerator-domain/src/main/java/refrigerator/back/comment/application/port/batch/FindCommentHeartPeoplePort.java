package refrigerator.back.comment.application.port.batch;

import refrigerator.back.comment.application.domain.CommentHeartPeople;

import java.util.List;

public interface FindCommentHeartPeoplePort {

    List<CommentHeartPeople> findByCommentID(Long commentId);
}
