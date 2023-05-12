package refrigerator.back.comment.adapter.out.repository.query;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.adapter.out.dto.OutCommentDTO;
import refrigerator.back.comment.adapter.out.dto.QOutCommentDTO;
import refrigerator.back.comment.adapter.out.persistence.CommentSortCondition;

import java.util.List;

import static refrigerator.back.comment.application.domain.QComment.*;
import static refrigerator.back.comment.application.domain.QCommentHeart.*;
import static refrigerator.back.member.application.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<OutCommentDTO> findCommentPreviewList(Long recipeId, Pageable page) {
        List<OutCommentDTO> content = jpaQueryFactory
                .select(new QOutCommentDTO(
                        comment.commentID,
                        member.nickname,
                        commentHeart.count,
                        comment.createDate,
                        comment.modifiedState,
                        comment.content,
                        member.email))
                .from(comment)
                .leftJoin(member).on(member.email.eq(comment.memberID))
                .leftJoin(commentHeart).on(commentHeart.commentId.eq(comment.commentID))
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

    private BooleanExpression notDeleted() {
        return comment.deletedState.eq(false);
    }

    private BooleanExpression recipeIdEq(Long recipeId) {
        return comment.recipeID.eq(recipeId);
    }

    @Override
    public List<OutCommentDTO> findCommentList(Long recipeId, String memberId, Pageable page, CommentSortCondition sortCondition) {
        return jpaQueryFactory
                .select(new QOutCommentDTO(
                        comment.commentID,
                        member.nickname,
                        commentHeart.count,
                        comment.createDate,
                        comment.modifiedState,
                        comment.content,
                        member.email))
                .from(comment)
                .leftJoin(member).on(member.email.eq(comment.memberID))
                .leftJoin(commentHeart).on(commentHeart.commentId.eq(comment.commentID))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .where(recipeIdEq(recipeId), notDeleted(), member.email.ne(memberId))
                .orderBy(conditionEq(sortCondition))
                .fetch();
    }

    @Override
    public List<OutCommentDTO> findMyCommentList(String memberId) {
        return jpaQueryFactory
                .select(new QOutCommentDTO(
                        comment.commentID,
                        member.nickname,
                        commentHeart.count,
                        comment.createDate,
                        comment.modifiedState,
                        comment.content,
                        member.email))
                .from(comment)
                .leftJoin(member).on(member.email.eq(comment.memberID))
                .leftJoin(commentHeart).on(commentHeart.commentId.eq(comment.commentID))
                .where(member.email.eq(memberId))
                .orderBy(comment.createDate.desc())
                .fetch();
    }

    private OrderSpecifier<?> conditionEq(CommentSortCondition sortCondition) {
        if (sortCondition == CommentSortCondition.HEART){
            return commentHeart.count.desc();
        }
        return comment.createDate.desc();
    }


}
