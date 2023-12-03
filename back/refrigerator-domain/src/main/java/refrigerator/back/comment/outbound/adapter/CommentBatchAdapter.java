package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.port.batch.DeleteCommentBatchPort;
import refrigerator.back.comment.application.port.batch.FindCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.batch.FindDeletedCommentPort;
import refrigerator.back.comment.outbound.repository.query.CommentBatchQueryRepository;
import refrigerator.back.comment.outbound.repository.redis.CommentHeartPeopleRedisRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentBatchAdapter implements DeleteCommentBatchPort, FindDeletedCommentPort, FindCommentHeartPeoplePort {

    private final CommentBatchQueryRepository commentBatchQueryRepository;
    private final CommentHeartPeopleRedisRepository commentHeartPeopleRedisRepository;

    @Override
    public Long deleteCommentHeart() {
        return commentBatchQueryRepository.deleteCommentHeart();
    }

    @Override
    public Long deleteComment() {
        return commentBatchQueryRepository.deleteComment();
    }

    @Override
    public void deleteCommentHeartPeople(CommentHeartPeople commentHeartPeople) {
        commentHeartPeopleRedisRepository.delete(commentHeartPeople);
    }

    @Override
    public List<Long> findDeletedComment() {
        return commentBatchQueryRepository.findDeletedComment();
    }

    @Override
    public List<CommentHeartPeople> findByCommentID(Long commentId) {
        return commentHeartPeopleRedisRepository.findByCommentId(commentId);
    }
}
