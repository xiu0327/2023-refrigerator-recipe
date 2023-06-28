package refrigerator.back.comment.adapter.repository.query;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.adapter.dto.OutCommentDTO;
import refrigerator.back.comment.adapter.dto.OutCommentHeartPeopleDto;
import refrigerator.back.comment.adapter.dto.QOutCommentDTO;
import refrigerator.back.comment.adapter.dto.QOutCommentHeartPeopleDto;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.domain.QCommentHeartPeople;

import java.util.List;

import static refrigerator.back.comment.application.domain.QComment.comment;
import static refrigerator.back.comment.application.domain.QCommentHeart.commentHeart;
import static refrigerator.back.comment.application.domain.QCommentHeartPeople.*;
import static refrigerator.back.member.application.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class CommentSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 댓글 미리보기 조회 쿼리
     * @param recipeId 레시피 식별자 값
     * @param page 페이징 처리를 위한 값
     * @return 전체 댓글 개수 + 하트 수 상위 3개 댓글 반환
     */
    public Page<OutCommentDTO> selectPreviewComments(Long recipeId, Pageable page) {
        List<OutCommentDTO> content = jpaQueryFactory
                .select(new QOutCommentDTO(
                        comment.commentId,
                        member.nickname,
                        commentHeart.count,
                        comment.commentRecord.createDateTime,
                        comment.commentRecord.modifiedState,
                        comment.content,
                        member.email))
                .from(comment)
                .leftJoin(member).on(member.email.eq(comment.writerId))
                .leftJoin(commentHeart).on(commentHeart.commentId.eq(comment.commentId))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .where(recipeIdEq(recipeId), notDeleted())
                .orderBy(commentHeart.count.desc())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .where(recipeIdEq(recipeId));

        return PageableExecutionUtils.getPage(content, page, count::fetchOne);
    }

    /**
     * 댓글 전체 조회 쿼리 (회원이 작성한 댓글 제외)
     * @param recipeId 레시피 식별자 값
     * @param memberId 사용자 id (이메일)
     * @param page 페이징 처리를 위한 값
     * @param sortCondition 정렬 조건 (좋아요 순 or 최신 순)
     * @return 정렬 조건에 맞는 상위 11개 댓글 추출
     */
    public List<OutCommentDTO> selectComments(Long recipeId, String memberId, Pageable page, CommentSortCondition sortCondition) {
        return jpaQueryFactory
                .select(new QOutCommentDTO(
                        comment.commentId,
                        member.nickname,
                        commentHeart.count,
                        comment.commentRecord.createDateTime,
                        comment.commentRecord.modifiedState,
                        comment.content,
                        member.email))
                .from(comment)
                .leftJoin(member).on(member.email.eq(comment.writerId))
                .leftJoin(commentHeart).on(commentHeart.commentId.eq(comment.commentId))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .where(recipeIdEq(recipeId), notDeleted(), member.email.ne(memberId))
                .orderBy(conditionEq(sortCondition))
                .fetch();
    }

    /**
     * 내가 쓴 댓글 조회
     * @param memberId 사용자 id (email)
     * @param recipeId 레시피 식별자값
     * @return
     */
    public List<OutCommentDTO> selectMyComments(String memberId, Long recipeId) {
        return jpaQueryFactory
                .select(new QOutCommentDTO(
                        comment.commentId,
                        member.nickname,
                        commentHeart.count,
                        comment.commentRecord.createDateTime,
                        comment.commentRecord.modifiedState,
                        comment.content,
                        member.email))
                .from(comment)
                .leftJoin(member).on(member.email.eq(comment.writerId))
                .leftJoin(commentHeart).on(commentHeart.commentId.eq(comment.commentId))
                .where(member.email.eq(memberId), notDeleted(), recipeIdEq(recipeId))
                .orderBy(comment.commentRecord.createDateTime.desc())
                .fetch();
    }

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

    private BooleanExpression notDeleted() {
        return comment.commentRecord.deletedState.eq(false);
    }

    private BooleanExpression recipeIdEq(Long recipeId) {
        return comment.recipeId.eq(recipeId);
    }

    private OrderSpecifier<?> conditionEq(CommentSortCondition sortCondition) {
        if (sortCondition == CommentSortCondition.HEART){
            return commentHeart.count.desc();
        }
        return comment.commentRecord.createDateTime.desc();
    }
}
