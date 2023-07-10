package refrigerator.back.comment.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.comment.outbound.repository.jpa.CommentHeartJpaRepository;
import refrigerator.back.comment.outbound.repository.jpa.CommentJpaRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, CommentUpdateQueryRepository.class, CommentHeartJpaRepository.class})
class CommentUpdateQueryRepositoryTest {

    @Autowired CommentUpdateQueryRepository query;
    @Autowired
    CommentJpaRepository commentDao;
    @Autowired
    CommentHeartJpaRepository commentHeartDao;

    @Test
    @DisplayName("댓글 내용 수정 쿼리 성공 테스트")
    void updateCommentToContentSuccessTest() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 6, 27, 10, 59);
        Comment comment = new Comment(1L, "email", "content", createDateTime);
        commentDao.save(comment);
        // when
        LocalDateTime now = LocalDateTime.of(2023, 6, 27, 11, 59);
        long result = query.updateCommentToContent(comment.getCommentId(), "update content", now);
        // then
        Optional<Comment> modifiedComment = commentDao.findById(comment.getCommentId());
        assertEquals(1, result);
        assertTrue(modifiedComment.isPresent());
        assertEquals("update content", modifiedComment.get().getContent());
        assertEquals(now, modifiedComment.get().getCommentRecord().isModified());
    }

    @Test
    @DisplayName("댓글 좋아요 개수 증가 쿼리 성공 테스트")
    void updateCommentToCountSuccessTest1(){
        // given
        long commentId = 1L;
        CommentHeart commentHeart = new CommentHeart(commentId);
        commentHeartDao.save(commentHeart);
        // when
        long result = query.updateCommentToCount(commentId, 1);
        Optional<CommentHeart> updateHeart = commentHeartDao.findById(commentId);
        assertEquals(1, result);
        assertTrue(updateHeart.isPresent());
        assertEquals(1, updateHeart.get().getCount());
    }

    @Test
    @DisplayName("댓글 좋아요 개수 감소 쿼리 실패 테스트")
    void updateCommentToCountSuccessTest2(){
        // given
        long commentId = 1L;
        CommentHeart commentHeart = new CommentHeart(commentId);
        commentHeartDao.save(commentHeart);
        // when
        long result = query.updateCommentToCount(commentId, -1);
        Optional<CommentHeart> updateHeart = commentHeartDao.findById(commentId);
        assertEquals(0, result);
        assertTrue(updateHeart.isPresent());
        assertEquals(0, updateHeart.get().getCount());
    }
}