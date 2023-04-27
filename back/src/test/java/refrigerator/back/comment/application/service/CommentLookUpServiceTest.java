package refrigerator.back.comment.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;
import refrigerator.back.comment.adapter.in.dto.response.InCommentListDTO;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;
import refrigerator.back.global.TestData;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@Slf4j
class CommentLookUpServiceTest {

    @Autowired CommentLookUpService commentLookUpService;
    @Autowired WriteCommentUseCase writeCommentUseCase;
    @Autowired TestData testData;

    @Test
    void 댓글_조회_하트순() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        for (int i = 0 ; i < 40; i++){
            writeCommentUseCase.write(recipeId, memberId, "댓글_" + i);
        }
        // when
        int size = 11;
        List<InCommentDTO> comments = commentLookUpService.findCommentsByHeart(recipeId, 0, size);
        // then
        for (InCommentDTO comment : comments) {
            assertNotNull(comment.getCommentId());
            assertNotNull(comment.getDate());
            assertNotNull(comment.getContent());
            assertNotNull(comment.getHeart());
            assertNotNull(comment.getNickname());
            assertNotNull(comment.getModifiedState());
        }
        assertThat(comments.size()).isEqualTo(size);
    }

    @Test
    void 댓글_조회_최근_작성순() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        for (int i = 0 ; i < 40; i++){
            writeCommentUseCase.write(recipeId, memberId, "댓글_" + i);
        }
        // when
        int size = 11;
        List<InCommentDTO> comments = commentLookUpService.findCommentsByDate(recipeId, 0, size);
        Long firstId = comments.get(0).getCommentId() + 1;
        // then
        for (InCommentDTO comment : comments) {
            assertThat(comment.getCommentId()).isEqualTo(--firstId);
            assertNotNull(comment.getCommentId());
            assertNotNull(comment.getDate());
            assertNotNull(comment.getContent());
            assertNotNull(comment.getHeart());
            assertNotNull(comment.getNickname());
            assertNotNull(comment.getModifiedState());
        }
        assertThat(comments.size()).isEqualTo(size);
    }

    @Test
    void 댓글_미리보기() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        int allSize = 40;
        for (int i = 0; i < allSize; i++){
            writeCommentUseCase.write(recipeId, memberId, "댓글_" + i);
        }
        // when
        int size = 3;
        InCommentListDTO result = commentLookUpService.findCommentPreviews(recipeId, size);
        List<InCommentDTO> comments = result.getComments();
        Integer count = result.getCount();
        // then
        for (InCommentDTO comment : comments) {
            assertNotNull(comment.getCommentId());
            assertNotNull(comment.getDate());
            assertNotNull(comment.getContent());
            assertNotNull(comment.getHeart());
            assertNotNull(comment.getNickname());
            assertNotNull(comment.getModifiedState());
        }
        assertThat(comments.size()).isEqualTo(size);
        assertThat(count).isEqualTo(allSize);
    }
}