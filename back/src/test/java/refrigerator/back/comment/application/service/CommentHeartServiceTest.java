package refrigerator.back.comment.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.in.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.BusinessException;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentHeartServiceTest {

    @Autowired WriteCommentUseCase writeCommentUseCase;
    @Autowired DeleteCommentUseCase deleteCommentUseCase;
    @Autowired CommentHeartService commentHeartService;
    @Autowired EntityManager em;
    @Autowired TestData testData;

    @Test
    void 하트수_증가_성공() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        Long commentId = writeCommentUseCase.write(recipeId, memberId, "댓글");
        // when
        commentHeartService.addHeart(commentId);
        // then
        CommentHeart commentHeart = em.find(CommentHeart.class, commentId);
        assertThat(commentHeart.getCount()).isEqualTo(1);
        assertThat(commentHeart.getDeleteStatus()).isFalse();
    }

    @Test
    void 하트수_증가_실패() {
        /* 이미 삭제된 댓글의 하트를 증가시키려는 경우, 에러 발생 */
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        Long commentId = writeCommentUseCase.write(recipeId, memberId, "댓글");
        deleteCommentUseCase.delete(memberId, commentId);
        // when & then
        assertThrows(BusinessException.class, () -> {
            try{
                commentHeartService.addHeart(commentId);
            }catch (BusinessException e){
                CommentHeart commentHeart = em.find(CommentHeart.class, commentId);
                assertThat(commentHeart.getDeleteStatus()).isTrue();
                assertThat(e.getBasicExceptionType()).isEqualTo(CommentExceptionType.DELETE_COMMENT);
                throw e;
            }
        });
    }

    @Test
    void 하트수_감소() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        Long commentId = writeCommentUseCase.write(recipeId, memberId, "댓글");
        // when
        commentHeartService.addHeart(commentId);
        commentHeartService.reduceHeart(commentId);
        // then
        CommentHeart commentHeart = em.find(CommentHeart.class, commentId);
        assertThat(commentHeart.getCount()).isEqualTo(0);
        assertThat(commentHeart.getDeleteStatus()).isFalse();
    }
}