package refrigerator.back.comment.adapter.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import refrigerator.back.comment.adapter.repository.dao.CommentHeartPeopleRepository;
import refrigerator.back.comment.adapter.repository.dao.CommentHeartRepository;
import refrigerator.back.comment.adapter.repository.dao.CommentRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.global.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("main")
@Import({QuerydslConfig.class, CommentDeleteQueryRepository.class, CommentHeartRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentDeleteQueryRepositoryTest {

    @Autowired CommentDeleteQueryRepository query;
    @Autowired CommentRepository commentDao;
    @Autowired CommentHeartRepository commentHeartDao;
    @Autowired CommentHeartPeopleRepository commentHeartPeopleDao;

    // TODO : LocalDateTime.now()말고 동일한 시간을 주입해주길 바람.
    
    @Test
    @DisplayName("댓글 삭제 쿼리 성공 테스트")
    void deleteCommentSuccessTest() {
        // given
        Comment comment = new Comment(1L, "email", "content", LocalDateTime.now());
        commentDao.save(comment);
        // when
        long result = query.deleteComment(comment.getCommentId());
        // then
        Optional<Comment> deleted = commentDao.findById(comment.getCommentId());
        assertEquals(1, result);
        assertTrue(deleted.isPresent());
        assertTrue(deleted.get().isDeleted());
    }

    @Test
    @DisplayName("댓글 좋아요 삭제 쿼리 성공 테스트")
    void deleteCommentHeart() {
        // given
        CommentHeart commentHeart = new CommentHeart(1L);
        commentHeartDao.save(commentHeart);
        // when
        long result = query.deleteCommentHeart(commentHeart.getCommentId());
        // then
        Optional<CommentHeart> deleted = commentHeartDao.findById(commentHeart.getCommentId());
        assertEquals(1, result);
        assertTrue(deleted.isPresent());
        assertTrue(deleted.get().isDeleted());
    }

    @Test
    @DisplayName("좋아요를 누른 회원 삭제 쿼리 성공 테스트")
    void deleteCommentHeartPeople() {
        // given
        CommentHeartPeople commentHeartPeople = new CommentHeartPeople("email", 1L);
        commentHeartPeopleDao.save(commentHeartPeople);
        // when
        long result = query.deleteCommentHeartPeople(commentHeartPeople.getCommentId());
        // then
        Optional<CommentHeartPeople> deleted = commentHeartPeopleDao.findById(commentHeartPeople.getCommentId());
        assertEquals(1, result);
        assertTrue(deleted.isPresent());
        assertTrue(deleted.get().isDeleted());
    }
}