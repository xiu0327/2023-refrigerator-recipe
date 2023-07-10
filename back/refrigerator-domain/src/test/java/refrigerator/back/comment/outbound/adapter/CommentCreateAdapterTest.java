package refrigerator.back.comment.outbound.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.outbound.repository.jpa.CommentHeartJpaRepository;
import refrigerator.back.comment.outbound.repository.jpa.CommentJpaRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.out.CreateCommentPort;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, CommentHeartJpaRepository.class})
@TestDataInit(value = "/member.sql")
class CommentCreateAdapterTest {

    @Autowired
    CommentJpaRepository commentRepository;
    @Autowired
    CommentHeartJpaRepository commentHeartRepository;
    private CreateCommentPort createCommentPort;

    @BeforeEach
    void init(){
        createCommentPort = new CommentCreateAdapter(commentRepository, commentHeartRepository);;
    }

    @Test
    @DisplayName("댓글 저장 성공 테스트")
    void createSuccessTest() {
        // given
        LocalDateTime time = LocalDateTime.of(2023, 6, 29, 4, 39);
        Comment comment = new Comment(1L, "writerId", "content", time);
        // when
        Long commentId = createCommentPort.create(comment);
        // then
        Optional<Comment> findOneComment = commentRepository.findById(commentId);
        Optional<CommentHeart> findOneCommentHeart = commentHeartRepository.findById(commentId);
        assertTrue(findOneComment.isPresent());
        assertTrue(findOneCommentHeart.isPresent());
    }
}