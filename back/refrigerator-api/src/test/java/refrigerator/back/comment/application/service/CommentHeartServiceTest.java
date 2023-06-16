package refrigerator.back.comment.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.port.in.comment.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.domain.BusinessException;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
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
        Long recipeId = 21L;
        Long commentId = writeCommentUseCase.write(recipeId, memberId, "댓글");
        // when
        commentHeartService.addHeart(memberId, commentId);
        // then
        CommentHeart commentHeart = em.find(CommentHeart.class, commentId);
        CommentHeartPeople commentHeartPeople = testData.findLikedPeopleList(memberId, commentId);
        assertThat(commentHeart.getCount()).isEqualTo(1);
        assertThat(commentHeart.getDeleteStatus()).isFalse();
        assertThat(commentHeartPeople).isNotNull();
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
                commentHeartService.addHeart(memberId, commentId);
            }catch (BusinessException e){
                CommentHeart commentHeart = em.find(CommentHeart.class, commentId);
                assertThat(commentHeart.getDeleteStatus()).isTrue();
                assertThat(e.getBasicExceptionType()).isEqualTo(CommentExceptionType.DELETE_COMMENT);
                throw e;
            }
        });
    }

    @Test
    void 중복된_하트수_증가_실패() {
        /* 하트가 눌러진 댓글에 좋아요 증가 요청을 중복으로 보낼 때, 에러 발생 */
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        Long commentId = writeCommentUseCase.write(recipeId, memberId, "댓글");
        commentHeartService.addHeart(memberId, commentId);
        // when & then
        assertThrows(BusinessException.class, () -> {
            try{
                commentHeartService.addHeart(memberId, commentId);
            }catch (BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(CommentExceptionType.DUPLICATE_HEART_REQUEST);
                throw e;
            }
        });
        assertThat(em.find(CommentHeart.class, commentId).getCount()).isEqualTo(1);
    }

    @Test
    void 하트수_감소() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 20L;
        Long commentId = writeCommentUseCase.write(recipeId, memberId, "댓글");
        // when
        commentHeartService.addHeart(memberId, commentId);
        commentHeartService.reduceHeart(memberId, commentId);
        // then
        CommentHeart commentHeart = em.find(CommentHeart.class, commentId);
        assertThat(commentHeart.getCount()).isEqualTo(0);
        assertThat(commentHeart.getDeleteStatus()).isFalse();
        assertThat(testData.findLikedPeopleList(memberId, commentId).getDeleteStatus()).isTrue();
    }

    @Test
    void 중복된_하트수_감소_실패() {
        /* 하트가 눌러진 댓글에 좋아요 감소 요청을 중복으로 보낼 때, 에러 발생 */
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 20L;
        Long commentId = writeCommentUseCase.write(recipeId, memberId, "댓글");
        // when & then
        assertThrows(BusinessException.class, () -> {
            try{
                commentHeartService.reduceHeart(memberId, commentId);
            }catch (BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(CommentExceptionType.DUPLICATE_HEART_REQUEST);
                throw e;
            }
        });
        assertThat(em.find(CommentHeart.class, commentId).getCount()).isEqualTo(0);
    }

    @Test
    void 회원이_누른_하트_목록(){
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        for (int i = 0 ; i < 10 ; i++){
            Long commentId = writeCommentUseCase.write(recipeId, memberId, "댓글");
            if (i % 2 == 0){
                commentHeartService.addHeart(memberId, commentId);
            }
        }
        // when
        List<Long> result = commentHeartService.findLikedPeople(memberId);
        assertThat(result.size()).isEqualTo(5);
    }
}