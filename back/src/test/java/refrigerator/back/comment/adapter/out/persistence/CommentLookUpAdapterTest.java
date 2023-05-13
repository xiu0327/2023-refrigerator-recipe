package refrigerator.back.comment.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentDto;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;
import refrigerator.back.global.TestData;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentLookUpAdapterTest {

    @Autowired CommentLookUpAdapter commentLookUpAdapter;
    @Autowired TestData testData;
    @Autowired WriteCommentUseCase commentUseCase;

    @Test
    @DisplayName("query문에 email도 가져오도록 수정 -> 제대로 가져오는지 확인하기 위한 테스트")
    void findCommentPreviewList() {
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 10L;
        commentUseCase.write(recipeId, memberId, "정말 맛있어요!");
        List<CommentDto> result = commentLookUpAdapter.findCommentPreviewList(recipeId, 11).getComments();
        for (CommentDto dto : result) {
            Assertions.assertNotNull(dto.getMemberId());
        }
    }

    @Test
    @DisplayName("query 문에 email 도 가져오도록 수정 -> 단, 자신이 작성한 댓글은 제외되어야 함")
    void findCommentListByHeart() {
        String memberId1 = testData.createMemberByEmail("email123@gmail.com");
        String memberId2 = testData.createMemberByEmail("email1234@gmail.com");
        Long recipeId = 10L;
        commentUseCase.write(recipeId, memberId1, "memberId1 : 정말 맛있어요 ~");
        commentUseCase.write(recipeId, memberId2, "memberId2 : 정말 맛있어요 ~");
        List<CommentDto> result = commentLookUpAdapter.findCommentListByHeart(recipeId, memberId1, 0, 11);
        assertThat(result.stream().anyMatch(commentDto -> commentDto.getMemberId().equals(memberId1))).isFalse();
    }

    @Test
    @DisplayName("현재 로그인한 사용자의 댓글만 가져오는 쿼리문")
    void findMyCommentList(){
        String memberId1 = testData.createMemberByEmail("email123@gmail.com");
        String memberId2 = testData.createMemberByEmail("email1234@gmail.com");
        Long recipeId = 10L;
        commentUseCase.write(recipeId, memberId1, "memberId1 : 정말 맛있어요 ~");
        commentUseCase.write(recipeId, memberId2, "memberId2 : 정말 맛있어요 ~");
        List<CommentDto> result = commentLookUpAdapter.findMyComments(memberId1, recipeId);
        assertThat(result.stream().anyMatch(commentDto -> commentDto.getMemberId().equals(memberId2))).isFalse();
    }
}