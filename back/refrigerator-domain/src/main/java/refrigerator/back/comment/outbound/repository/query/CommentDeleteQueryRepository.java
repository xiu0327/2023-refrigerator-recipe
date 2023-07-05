package refrigerator.back.comment.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static refrigerator.back.comment.application.domain.QComment.comment;
import static refrigerator.back.comment.application.domain.QCommentHeart.commentHeart;

@Repository
@RequiredArgsConstructor
public class CommentDeleteQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     * 댓글 삭제 쿼리
     * @param id 삭제할 댓글 식별자
     * @return 성공 -> 1 반환, 실패 -> 0 또는 2 이상
     */
    public long deleteComment(Long id){
        long result = jpaQueryFactory
                .update(comment)
                .set(comment.commentRecord.deletedState, true)
                .where(comment.commentId.eq(id),
                        comment.commentRecord.deletedState.eq(false))
                .execute();
        em.flush();
        em.clear();
        return result;
    }

    /**
     * 댓글 하트 삭제 쿼리
     * @param id 삭제할 댓글 식별자
     * @return 성공 -> 1 반환, 실패 -> 0 또는 2 이상
     */
    public long deleteCommentHeart(Long id) {
        long result = jpaQueryFactory
                .update(commentHeart)
                .set(commentHeart.deleteStatus, true)
                .where(commentHeart.commentId.eq(id),
                        commentHeart.deleteStatus.eq(false))
                .execute();
        em.flush();
        em.clear();
        return result;
    }

}
