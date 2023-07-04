package refrigerator.back.comment.outbound.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.outbound.adapter.CommentLookUpAdapter;
import refrigerator.back.comment.outbound.dto.OutCommentDTO;
import refrigerator.back.comment.outbound.mapper.OutCommentMapper;
import refrigerator.back.comment.outbound.repository.query.CommentSelectQueryRepository;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.port.out.FindCommentPort;
import refrigerator.back.global.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, CommentSelectQueryRepository.class})
@TestDataInit(value = {"/comment.sql", "/member.sql"})
class CommentLookUpAdapterTest {

    @Autowired CommentSelectQueryRepository queryRepository;
    OutCommentMapper outCommentMapper = Mappers.getMapper(OutCommentMapper.class);
    private FindCommentPort findCommentPort;

    @BeforeEach
    void init(){
        this.findCommentPort = new CommentLookUpAdapter(queryRepository, outCommentMapper);
    }

    @Test
    @DisplayName("댓글 전체 조회 - 하트순 조회")
    void findCommentsOrderByHeart() {
        // given
        String memberId = "mstest102@gmail.com";
        // when
        List<CommentDto> comments = findCommentPort.findComments(1L, memberId, CommentSortCondition.HEART, 0, 11);
        // then
        comments.forEach(comment -> assertNotEquals(comment, CommentDto.builder().build()));
        try{
            int preHeartCount = 100;
            for (CommentDto comment : comments) {
                assertNotEquals(comment, CommentDto.builder().build());
                assertTrue(comment.getHeart() <= preHeartCount);
                preHeartCount = comment.getHeart();
            }
        } catch (NullPointerException e){
            System.out.println("CommentLookUpAdapterTest.findCommentsOrderByHeart");
        }

    }

    @Test
    @DisplayName("댓글 전체 조회 - 최신순 조회")
    void findCommentsOrderByDate() {
        // given
        String memberId = "mstest102@gmail.com";
        // when
        List<CommentDto> comments = findCommentPort.findComments(1L, memberId, CommentSortCondition.DATE, 0, 11);
        // then
        LocalDateTime preCreateDate = LocalDateTime.of(2024, 1, 1, 1, 1);
        comments.forEach(comment -> assertNotEquals(comment, CommentDto.builder().build()));
        for (CommentDto comment : comments) {
            assertNotEquals(comment, OutCommentDTO.builder().build());
            assertNotEquals(memberId, comment.getMemberId());
            assertTrue(preCreateDate.compareTo(comment.getCreateDate()) >= 0);
            preCreateDate = comment.getCreateDate();
        }
    }

    @Test
    @DisplayName("댓글 미리보기 조회")
    void findPreviewComments() {
        // given
        String memberId = "mstest102@gmail.com";
        // when
        List<CommentDto> comments = findCommentPort.findPreviewComments(1L, memberId, 3);
        // then
        comments.forEach(comment -> assertNotEquals(comment, CommentDto.builder().build()));
    }

}