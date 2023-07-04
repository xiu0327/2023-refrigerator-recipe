package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.CommentHeartPeople;

import java.util.Map;
import java.util.Optional;

public interface FindCommentHeartPeoplePort {
    Map<Long, Object> findPeopleMap(String memberId);
    Optional<CommentHeartPeople> findPeopleOne(Long commentId, String memberId);
}
