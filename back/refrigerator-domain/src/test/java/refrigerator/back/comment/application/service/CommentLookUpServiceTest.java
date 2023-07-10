package refrigerator.back.comment.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import refrigerator.back.annotation.IntegrationTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.dto.InCommentDto;
import refrigerator.back.comment.application.port.in.FindCommentsUseCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
@ContextConfiguration(classes = CommentServiceTestConfiguration.class)
@TestDataInit(value = {"/comment.sql", "/member.sql"})
class CommentLookUpServiceTest {

    @Autowired FindCommentsUseCase findCommentsUseCase;

    @Test
    @DisplayName("전체 댓글 조회 -> 하트순 조회")
    void findCommentsOrderByHeart() {
        // given
        String memberId = "mstest102@gmail.com";
        String expectedDate = "2 달 전";
        int preCount = -1;
        // when
        List<InCommentDto> comments = findCommentsUseCase.findComments(1L, memberId, CommentSortCondition.HEART, 0, 11);
        // then
        for (InCommentDto comment : comments) {
            assertNotEquals(comment, InCommentDto.builder().build());
            assertEquals(expectedDate, comment.getDate());
            assertTrue(comment.getHeart() >= preCount);
            preCount = comment.getHeart();
        }
    }

    @Test
    @DisplayName("전체 댓글 조회 -> 최신순 조회")
    void findCommentsOrderByDate() {
        // given
        String memberId = "mstest102@gmail.com";
        String expectedDate = "2 달 전";
        // when
        List<InCommentDto> comments = findCommentsUseCase.findComments(1L, memberId, CommentSortCondition.DATE, 0, 11);
        // then
        comments.forEach(comment -> {
            assertNotEquals(comment, InCommentDto.builder().build());
            assertEquals(expectedDate, comment.getDate());
        });
    }

    @Test
    @DisplayName("댓글 미리 보기")
    void findCommentsPreview() {
        // given
        String memberId = "mstest102@gmail.com";
        String expectedDate = "2 달 전";
        // when
        List<InCommentDto> comments = findCommentsUseCase.findCommentsPreview(1L, memberId, 3);
        // then
        comments.forEach(comment -> {
            assertNotEquals(comment, InCommentDto.builder().build());
            assertEquals(expectedDate, comment.getDate());
        });
    }
}