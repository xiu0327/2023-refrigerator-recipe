package refrigerator.back.comment.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.global.TestData;

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
        log.info("create date = {}", comment.getCreateDate());
        log.info("modify date = {}", comment.getLastModifiedDate());
    }

    @Test
    void 댓글_삭제() throws InterruptedException {
        // given
        String memberId = testData.createMemberByEmail("email123@gamil.com");
        Long recipeId = 1L;
        String content = "댓글";
        Long commentId = commentService.write(recipeId, memberId, content);
        // when
        Thread.sleep(1000);
        commentService.delete(commentId);
        em.flush();
        em.clear();
        // then
        Comment comment = em.find(Comment.class, commentId);
        assertThat(comment.getDeletedState()).isTrue();
        assertThat(comment.getCreateDate()).isNotEqualTo(comment.getLastModifiedDate());
    }

    @Test
    void 댓글_수정() throws InterruptedException {
        // given
        String memberId = testData.createMemberByEmail("email123@gamil.com");
        Long recipeId = 1L;
        String content = "댓글";
        Long commentId = commentService.write(recipeId, memberId, content);
        // when
        Thread.sleep(1000);
        String newContent = "새로운 댓글";
        commentService.edit(commentId, newContent);
        em.flush();
        em.clear();
        // then
        Comment comment = em.find(Comment.class, commentId);
        assertThat(comment.getContent()).isEqualTo(newContent);
        assertThat(comment.getModifiedState()).isTrue();
        assertThat(comment.getCreateDate()).isNotEqualTo(comment.getLastModifiedDate());
    }
}