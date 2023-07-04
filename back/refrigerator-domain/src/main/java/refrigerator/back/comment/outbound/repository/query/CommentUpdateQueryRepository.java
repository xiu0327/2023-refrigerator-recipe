package refrigerator.back.comment.outbound.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static refrigerator.back.comment.application.domain.QComment.*;
import static refrigerator.back.comment.application.domain.QCommentHeart.commentHeart;

@Repository
@RequiredArgsConstructor
public class CommentUpdateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     * 댓글 수정 쿼리 : 내용 수정 -> 수정 일자와 수정 상태 변경
     * @param id 내용을 수정할 댓글의 Id
     * @param content 수정 내용
     * @param now 수정 일자
     * @return 성공 -> 1 반환, 실패 -> 0 또는 2 이상
     */
    public long updateCommentToContent(Long id, String content, LocalDateTime now){
        long result = jpaQueryFactory.update(comment)
                .set(comment.content, content)
                .set(comment.commentRecord.lastModifiedDateTime, now)
                .set(comment.commentRecord.modifiedState, true)
                .where(comment.commentId.eq(id))
                .execute();
        em.flush();
        em.clear();
        return result;
    }

    /**
     * 댓글 하트 수 갱신 쿼리
     * @param commentId 하트 수를 갱신할 댓글 Id
     * @param value 증가 -> value=1, 감소 -> value=-1
     * @return 성공 -> 1 반환, 실패 -> 0 또는 2 이상
     */
    public long updateCommentToCount(Long commentId, int value) {
        long result = jpaQueryFactory
                .update(commentHeart)
                .set(commentHeart.count, commentHeart.count.add(value))
                .where(decideUpdateCondition(value),
                        commentHeart.commentId.eq(commentId),
                        commentHeart.deleteStatus.eq(false))
                .execute();
        em.flush();
        em.clear();
        return result;
    }

    private BooleanExpression decideUpdateCondition(int value) {
        if (value < 0){
            return commentHeart.count.goe(1);
        }
        return commentHeart.count.goe(0);
    }
}
