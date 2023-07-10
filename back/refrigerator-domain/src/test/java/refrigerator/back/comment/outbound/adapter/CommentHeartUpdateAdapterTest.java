package refrigerator.back.comment.outbound.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.domain.CommentHeartValue;
import refrigerator.back.comment.outbound.repository.jpa.CommentHeartJpaRepository;
import refrigerator.back.comment.outbound.repository.query.CommentUpdateQueryRepository;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class,
        CommentHeartJpaRepository.class,
        CommentHeartCountUpdateAdapter.class,
        CommentUpdateQueryRepository.class})
class CommentHeartUpdateAdapterTest {

    @Autowired
    CommentHeartCountUpdateAdapter commentHeartUpdateAdapter;
    @Autowired CommentHeartJpaRepository commentHeartJpaRepository;

    @Test
    @DisplayName("댓글 하트수 증가 성공 테스트")
    void changeForAddSuccessTest() {
        // given
        long commentId = 1L;
        CommentHeart commentHeart = new CommentHeart(commentId);
        commentHeartJpaRepository.save(commentHeart);
        // when
        Long result = commentHeartUpdateAdapter.change(commentId, CommentHeartValue.ADD);
        // then
        assertEquals(1, result);
    }

    @Test
    @DisplayName("댓글 하트수 감소 성공 테스트")
    void changeForReduceSuccessTest() {
        // given
        long commentId = 1L;
        CommentHeart commentHeart = new CommentHeart(commentId);
        commentHeartJpaRepository.save(commentHeart);
        // when
        Long addResult = commentHeartUpdateAdapter.change(commentId, CommentHeartValue.ADD);
        Long reduceResult = commentHeartUpdateAdapter.change(commentId, CommentHeartValue.REDUCE);
        // then
        assertEquals(1, addResult);
        assertEquals(1, reduceResult);
    }
}