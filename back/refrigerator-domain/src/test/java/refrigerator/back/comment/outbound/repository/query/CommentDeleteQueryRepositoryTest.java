package refrigerator.back.comment.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.comment.outbound.repository.jpa.CommentHeartJpaRepository;
import refrigerator.back.comment.outbound.repository.jpa.CommentJpaRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.global.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, CommentDeleteQueryRepository.class})
class CommentDeleteQueryRepositoryTest {

    @Autowired CommentDeleteQueryRepository query;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("댓글 삭제 쿼리 성공 테스트")
    void deleteCommentSuccessTest() {
        // given
        Comment comment = new Comment(1L, "email", "content", LocalDateTime.now());
        Long commentId = (Long) em.persistAndGetId(comment);
        // when
        long result = query.deleteComment(comment.getCommentId());
        // then
        Optional<Comment> deleted = Optional.ofNullable(em.find(Comment.class, commentId));
        assertEquals(1, result);
        assertTrue(deleted.isPresent());
        assertTrue(deleted.get().isDeleted());
    }

    @Test
    @DisplayName("댓글 좋아요 삭제 쿼리 성공 테스트")
    void deleteCommentHeart() {
        // given
        CommentHeart commentHeart = new CommentHeart(1L);
        Long commentId = (Long) em.persistAndGetId(commentHeart);
        // when
        long result = query.deleteCommentHeart(commentHeart.getCommentId());
        // then
        Optional<CommentHeart> deleted = Optional.ofNullable(em.find(CommentHeart.class, commentId));
        assertEquals(1, result);
        assertTrue(deleted.isPresent());
        assertTrue(deleted.get().isDeleted());
    }

}