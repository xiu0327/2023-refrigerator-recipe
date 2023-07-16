package refrigerator.back.comment.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.dto.InCommentDto;
import refrigerator.back.comment.application.port.in.FindCommentsUseCase;
import refrigerator.back.global.config.QuerydslConfig;

import java.util.List;

@ContextConfiguration(classes = CommentLookUpTestConfiguration.class)
@DataJpaTest
@AutoConfigureDataRedis
@Import(QuerydslConfig.class)
@TestDataInit(value = {"/comment.sql", "/member.sql"})
class CommentLookUpServiceTest {
    @Autowired FindCommentsUseCase findCommentsUseCase;

    @Test
    @DisplayName("전체 댓글 조회 -> 하트순 조회")
    void findCommentsOrderByHeart() {
        // given
        String memberId = "mstest102@gmail.com";
        // when
        List<InCommentDto> comments = findCommentsUseCase.findComments(1L, memberId, CommentSortCondition.HEART, 0, 11);
        // then
        comments.forEach(comment -> Assertions.assertNotEquals(comment, InCommentDto.builder().build()));
    }

    @Test
    @DisplayName("전체 댓글 조회 -> 최신순 조회")
    void findCommentsOrderByDate() {
        // given
        String memberId = "mstest102@gmail.com";
        // when
        List<InCommentDto> comments = findCommentsUseCase.findComments(1L, memberId, CommentSortCondition.DATE, 0, 11);
        // then
        comments.forEach(comment -> Assertions.assertNotEquals(comment, InCommentDto.builder().build()));
    }

    @Test
    @DisplayName("댓글 미리 보기")
    void findCommentsPreview() {
        // given
        String memberId = "mstest102@gmail.com";
        // when
        List<InCommentDto> comments = findCommentsUseCase.findCommentsPreview(1L, memberId, 3);
        // then
        comments.forEach(comment -> Assertions.assertNotEquals(comment, InCommentDto.builder().build()));
    }
}