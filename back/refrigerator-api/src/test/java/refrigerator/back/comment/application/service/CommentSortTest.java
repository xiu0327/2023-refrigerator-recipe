package refrigerator.back.comment.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;
import refrigerator.back.comment.application.port.in.comment.FindCommentListUseCase;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class CommentSortTest {

    @Autowired FindCommentListUseCase findCommentListUseCase;
    @Autowired WriteCommentUseCase writeCommentUseCase;

    @Test
    @DisplayName("댓글이 제대로 정렬되는지 확인")
    void commentSort(){
        String memberId = "email123@gmail.com";
        Long recipeId = 1L;
        writeCommentUseCase.write(recipeId, "test123@gmail.com", "정말 맛있어요!");
        List<InCommentDTO> result = findCommentListUseCase.findCommentsByDate(recipeId, memberId, 0, 11);
        for (InCommentDTO dto : result) {
            log.info(dto.getDate());
        }
    }
}
