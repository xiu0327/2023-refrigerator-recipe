package refrigerator.back.comment.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
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
        String otherMemberId = testData.createMemberByEmail("email1233@gmail.com");
        Long recipeId = 1L;
        for (int i = 0 ; i < 40; i++){
            writeCommentUseCase.write(recipeId, memberId, "댓글_" + i);
        }
        // when
        int size = 11;
        List<InCommentDTO> comments = commentLookUpService.findCommentsByHeart(recipeId, otherMemberId,0, size);
        // then
        for (InCommentDTO comment : comments) {
            assertNotNull(comment.getCommentID());
            assertNotNull(comment.getDate());
            assertNotNull(comment.getContent());
            assertNotNull(comment.getHeart());
            assertNotNull(comment.getNickname());
            assertNotNull(comment.getIsMyComment());
            assertNotNull(comment.getModifiedState());
        }
        assertThat(comments.size()).isEqualTo(size);
    }

    @Test
    void 댓글_조회_최근_작성순() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        String otherMemberId = testData.createMemberByEmail("email1233@gmail.com");
        Long recipeId = 1L;
        for (int i = 0 ; i < 40; i++){
            writeCommentUseCase.write(recipeId, memberId, "댓글_" + i);
        }
        // when
        int size = 11;
        List<InCommentDTO> comments = commentLookUpService.findCommentsByDate(recipeId, otherMemberId,0, size);
        Long firstId = comments.get(0).getCommentID() + 1;
        // then
        for (InCommentDTO comment : comments) {
            assertThat(comment.getCommentID()).isEqualTo(--firstId);
            assertNotNull(comment.getCommentID());
            assertNotNull(comment.getDate());
            assertNotNull(comment.getContent());
            assertNotNull(comment.getHeart());
            assertNotNull(comment.getIsMyComment());
            assertNotNull(comment.getNickname());
            assertNotNull(comment.getModifiedState());
        }
        assertThat(comments.size()).isEqualTo(size);
    }

    @Test
    void 댓글_미리보기() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        String otherMemberId = testData.createMemberByEmail("email1233@gmail.com");
        Long recipeId = 1L;
        int allSize = 40;
        for (int i = 0; i < allSize; i++){
            writeCommentUseCase.write(recipeId, memberId, "댓글_" + i);
        }
        // when
        int size = 3;
        InCommentListDTO result = commentLookUpService.findCommentPreviews(recipeId, otherMemberId, size);
        List<InCommentDTO> comments = result.getComments();
        // then
        for (InCommentDTO comment : comments) {
            assertNotNull(comment.getCommentID());
            assertNotNull(comment.getDate());
            assertNotNull(comment.getContent());
            assertNotNull(comment.getHeart());
            assertNotNull(comment.getIsMyComment());
            assertNotNull(comment.getNickname());
            assertNotNull(comment.getModifiedState());
        }
        assertThat(comments.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("내가 쓴 댓글 목록 조회")
    void findMyComments(){
        String memberId1 = testData.createMemberByEmail("email123@gmail.com");
        String memberId2 = testData.createMemberByEmail("email1234@gmail.com");
        Long recipeId = 10L;
        writeCommentUseCase.write(recipeId, memberId1, "memberId1 : 정말 맛있습니다!");
        writeCommentUseCase.write(recipeId, memberId2, "memberId2 : 정말 맛있습니다!");
        List<InCommentDTO> result = commentLookUpService.findMyComments(memberId1, recipeId);
        assertThat(result.stream().anyMatch(inCommentDTO -> inCommentDTO.getIsMyComment() == Boolean.FALSE)).isFalse();
    }
}