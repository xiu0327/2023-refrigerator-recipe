package refrigerator.back.comment.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.domain.BusinessException;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired TestData testData;
    @Autowired EntityManager em;

    @Test
    void 댓글_생성() {
        // given
        String memberId = testData.createMemberByEmail("email123@gamil.com");
        Long recipeId = 1L;
        String content = "댓글";
        // when
        Long commentId = commentService.write(recipeId, memberId, content);
        // then
        Comment comment = em.find(Comment.class, commentId);
        assertThat(comment.getDeletedState()).isFalse();
        assertThat(comment.getModifiedState()).isFalse();
        assertThat(comment.getRecipeID()).isEqualTo(recipeId);
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getCreateDate()).isNotNull();
        assertThat(comment.getLastModifiedDate()).isNotNull();
    }

    @Test
    void 댓글_삭제() {
        // given
        String memberId = testData.createMemberByEmail("email123@gamil.com");
        Long recipeId = 1L;
        String content = "댓글";
        Long commentId = commentService.write(recipeId, memberId, content);
        // when
        commentService.delete(memberId, commentId);
        em.flush();
        em.clear();
        // then
        Comment comment = em.find(Comment.class, commentId);
        assertThat(comment.getDeletedState()).isTrue();
        assertThat(comment.getCreateDate()).isNotEqualTo(comment.getLastModifiedDate());
    }

    @Test
    void 댓글_삭제_실패(){
        /* 댓글을 작성한 회원이 아닌 다른 회원이 삭제하려는 경우, 에러 발생 */
        // given
        String memberId = testData.createMemberByEmail("email123@gamil.com");
        Long recipeId = 1L;
        String content = "댓글";
        Long commentId = commentService.write(recipeId, memberId, content);
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                commentService.delete("worng_email123@gmail.com", commentId);
            }catch(BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(CommentExceptionType.NO_EDIT_RIGHTS);
                throw e;
            }
        });
    }

    @Test
    void 댓글_수정() throws InterruptedException {
        // given
        String memberId = testData.createMemberByEmail("email123@gamil.com");
        Long recipeId = 1L;
        String content = "댓글";
        Long commentId = commentService.write(recipeId, memberId, content);
        // when
        String newContent = "새로운 댓글";
        commentService.edit(memberId, commentId, newContent);
        em.flush();
        em.clear();
        // then
        Comment comment = em.find(Comment.class, commentId);
        assertThat(comment.getContent()).isEqualTo(newContent);
        assertThat(comment.getModifiedState()).isTrue();
        assertThat(comment.getCreateDate()).isNotEqualTo(comment.getLastModifiedDate());
    }

    @Test
    void 댓글_수정_실패(){
        /* 댓글을 작성한 회원이 아닌 다른 회원이 수정하려는 경우, 에러 발생 */
        // given
        String memberId = testData.createMemberByEmail("email123@gamil.com");
        Long recipeId = 1L;
        String content = "댓글";
        Long commentId = commentService.write(recipeId, memberId, content);
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                commentService.edit("worng_email123@gmail.com", commentId, "새로운 댓글");
            }catch(BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(CommentExceptionType.NO_EDIT_RIGHTS);
                throw e;
            }
        });
    }

    @Test
    void 삭제된_댓글_수정_실패(){
        /* 삭제된 댓글을 수정하려는 경우, 에러 발생 */
        // given
        String memberId = testData.createMemberByEmail("email123@gamil.com");
        Long recipeId = 1L;
        String content = "댓글";
        Long commentId = commentService.write(recipeId, memberId, content);
        commentService.delete(memberId, commentId);
        em.flush();
        em.clear();
        // when
        assertThrows(BusinessException.class, () -> {
            try{
                commentService.edit(memberId, commentId, "새로운 댓글");
            }catch(BusinessException e){
                assertThat(e.getBasicExceptionType()).isEqualTo(CommentExceptionType.NOT_FOUND_COMMENT);
                throw e;
            }
        });
    }
}