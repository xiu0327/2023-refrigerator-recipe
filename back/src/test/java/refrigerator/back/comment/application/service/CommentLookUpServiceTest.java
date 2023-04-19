package refrigerator.back.comment.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;
import refrigerator.back.comment.adapter.in.dto.response.InCommentListDTO;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.back.global.TestData;

import javax.persistence.EntityManager;
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
    @Autowired EntityManager em;

    @Test
    void 댓글_조회() {
        // given
        String memberId = testData.createMemberByEmail("email123@gmail.com");
        Long recipeId = 1L;
        for (int i = 0 ; i < 40; i++){
            writeCommentUseCase.write(recipeId, memberId, "댓글_" + i);
        }
        // when
        int size = 11;
        List<InCommentDTO> comments = commentLookUpService.findComments(recipeId, 0, size);
        // then
        for (InCommentDTO comment : comments) {
//            log.info("comment = {}", comment);
            assertNotNull(comment.getCommentId());
            assertNotNull(comment.getCreateDate());
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
//        log.info("count = {}", count);
        // then
        for (InCommentDTO comment : comments) {
//            log.info("comment = {}", comment);
            assertNotNull(comment.getCommentId());
            assertNotNull(comment.getCreateDate());
            assertNotNull(comment.getContent());
            assertNotNull(comment.getHeart());
            assertNotNull(comment.getNickname());
            assertNotNull(comment.getModifiedState());
        }
        assertThat(comments.size()).isEqualTo(size);
        assertThat(count).isEqualTo(allSize);
    }
}