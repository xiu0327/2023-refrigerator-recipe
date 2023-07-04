package refrigerator.back.comment.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.outbound.dto.OutCommentHeartPeopleDto;
import refrigerator.back.comment.outbound.dto.QOutCommentHeartPeopleDto;

import java.util.List;

import static refrigerator.back.comment.application.domain.QCommentHeartPeople.commentHeartPeople;

@Repository
@RequiredArgsConstructor
public class CommentHeartPeopleSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 특정 댓글에 대하여 현재 사용자가 좋아요를 눌렀는지의 여부 조회
     * @param ids 댓글 id 리스트
     * @param memberId 현재 사용자 이메일(id)
     * @return 댓글 id와 좋아요를 누른 회원의 식별자 값 반환
     */
    public List<OutCommentHeartPeopleDto> selectCommentHeartPeopleByCommendIds(List<Long> ids, String memberId){
        return jpaQueryFactory.select(new QOutCommentHeartPeopleDto(
                        commentHeartPeople.commentId,
                        commentHeartPeople.id))
                .from(commentHeartPeople)
                .where(commentHeartPeople.commentId.in(ids), commentHeartPeople.memberId.eq(memberId))
                .fetch();
    }

    /**
     * 특정 commentId 와 memberId 를 가지는 좋아요를 누른 회원 개수 조회
     * @param peopleId 좋아요를 누른 회원의 식별자 값
     * @return 좋아요를 누른 회원 개수
     */
    public Long selectCommentHeartPeopleCount(Long peopleId){
        return jpaQueryFactory
                .select(commentHeartPeople.count())
                .from(commentHeartPeople)
                .where(commentHeartPeople.commentId.eq(peopleId),
                        commentHeartPeople.deleteStatus.eq(false))
                .fetchOne();
    }
}
