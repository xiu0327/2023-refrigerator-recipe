package refrigerator.back.comment.adapter.out.repository.query;


import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.domain.CommentHeartPeople;

import java.util.List;

public interface CommentHeartQueryRepository {
    long updateCommentHeartCount(Long commentId, int value);
    long deleteCommentHeartCount(Long commentId);
    void persistCommentHeart(CommentHeart commentHeart);
    Long persistCommentHeartPeople(CommentHeartPeople commentHeartPeople);
    void deleteCommentHeartPeople(String memberId, Long commentId);
    List<Long> findLikedListByMemberId(String memberId);
}
