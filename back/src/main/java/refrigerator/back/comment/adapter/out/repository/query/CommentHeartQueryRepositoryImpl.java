package refrigerator.back.comment.adapter.out.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.domain.CommentHeartPeople;

import javax.persistence.EntityManager;
import java.util.List;

import static refrigerator.back.comment.application.domain.QComment.comment;
import static refrigerator.back.comment.application.domain.QCommentHeart.commentHeart;
import static refrigerator.back.comment.application.domain.QCommentHeartPeople.commentHeartPeople;

@Repository
@RequiredArgsConstructor
public class CommentHeartQueryRepositoryImpl implements CommentHeartQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public long updateCommentHeartCount(Long commentId, int value) {
        long result = jpaQueryFactory
                .update(commentHeart)
                .set(commentHeart.count, commentHeart.count.add(value))
                .where(commentHeart.commentId.eq(commentId),
                        commentHeart.deleteStatus.eq(false))
                .execute();
        em.flush();
        em.clear();
        return result;
    }

    @Override
    public long deleteCommentHeartCount(Long commentId) {
        long result = jpaQueryFactory
                .update(commentHeart)
                .set(commentHeart.deleteStatus, true)
                .where(commentHeart.commentId.eq(commentId),
                        commentHeart.deleteStatus.eq(false))
                .execute();
        em.flush();
        em.clear();
        return result;
    }

    @Override
    public void persistCommentHeart(CommentHeart commentHeart) {
        em.persist(commentHeart);
    }

    @Override
    public Long persistCommentHeartPeople(CommentHeartPeople commentHeartPeople) {
        em.persist(commentHeartPeople);
        return commentHeartPeople.getId();
    }

    @Override
    public void deleteCommentHeartPeople(String memberId, Long commentId) {
        jpaQueryFactory.update(commentHeartPeople)
                .set(commentHeartPeople.deleteStatus, true)
                .where(commentHeartPeople.commentId.eq(commentId),
                        commentHeartPeople.memberId.eq(memberId))
                .execute();
        em.flush();
        em.clear();
    }

    @Override
    public List<Long> findLikedListByMemberId(String memberId) {
        return jpaQueryFactory.select(commentHeartPeople.commentId)
                .from(commentHeartPeople)
                .leftJoin(comment).on(comment.commentID.eq(commentHeartPeople.commentId))
                .where(
                        comment.deletedState.eq(false),
                        commentHeartPeople.memberId.eq(memberId),
                        commentHeartPeople.deleteStatus.eq(false))
                .fetch();
    }
}
