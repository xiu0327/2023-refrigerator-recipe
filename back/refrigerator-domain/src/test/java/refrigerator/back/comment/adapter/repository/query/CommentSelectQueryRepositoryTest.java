package refrigerator.back.comment.adapter.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.adapter.dto.OutCommentDTO;
import refrigerator.back.comment.adapter.dto.OutCommentHeartPeopleDto;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.global.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, CommentSelectQueryRepository.class})
@TestDataInit({"/comment.sql", "/member.sql"})
@Slf4j
class CommentSelectQueryRepositoryTest {

    @Autowired CommentSelectQueryRepository queryRepository;

    @Test
    @DisplayName("댓글 미리 보기 조회 성공 테스트")
    void selectPreviewComments() {
        int size = 3;
        Page<OutCommentDTO> result = queryRepository.selectPreviewComments(1L, PageRequest.of(0, size));
        assertEquals(size, result.getNumberOfElements());
        for (OutCommentDTO outCommentDTO : result.getContent()) {
            assertNotEquals(outCommentDTO, OutCommentDTO.builder().build());
        }
    }

    @Test
    @DisplayName("댓글 전체 하트순 조회 성공 테스트")
    void selectCommentsByHeartCountSuccessTest() {
        String memberId = "mstest102@gmail.com";
        int size = 5;
        List<OutCommentDTO> result = queryRepository.selectComments(1L, memberId, PageRequest.of(0, size), CommentSortCondition.HEART);
        assertEquals(size, result.size());
        int preHeartCount = 100;
        for (OutCommentDTO outCommentDTO : result) {
            assertNotEquals(outCommentDTO, OutCommentDTO.builder().build());
            assertTrue(outCommentDTO.getHeart() <= preHeartCount);
            preHeartCount = outCommentDTO.getHeart();
        }
    }

    @Test
    @DisplayName("댓글 전체 최신순 조회 성공 테스트")
    void selectCommentsByDateSuccessTest() {
        // given
        String memberId = "mstest102@gmail.com";
        int size = 5;
        LocalDateTime preCreateDate = LocalDateTime.of(2024, 1, 1, 1, 1);
        // when
        List<OutCommentDTO> result = queryRepository.selectComments(1L, memberId, PageRequest.of(0, size), CommentSortCondition.DATE);
        assertEquals(size, result.size());
        // then
        for (OutCommentDTO outCommentDTO : result) {
            assertNotEquals(outCommentDTO, OutCommentDTO.builder().build());
            assertNotEquals(memberId, outCommentDTO.getMemberId());
            assertTrue(preCreateDate.compareTo(outCommentDTO.getCreateDate()) >= 0);
            preCreateDate = outCommentDTO.getCreateDate();
        }
    }

    @Test
    @DisplayName("내가 쓴 댓글 조회하기 성공 테스트")
    void selectMyComments() {
        // given
        String memberId = "jktest101@gmail.com";
        // when
        List<OutCommentDTO> result = queryRepository.selectMyComments(memberId, 1L);
        // then
        for (OutCommentDTO outCommentDTO : result) {
            assertNotEquals(outCommentDTO, OutCommentDTO.builder().build());
            assertEquals(memberId, outCommentDTO.getMemberId());
        }
    }

    @Test
    @DisplayName("조회된 댓글 중 내가 하트를 누른 댓글 찾기 성공 테스트")
    void selectCommentHeartPeopleByCommendIds() {
        // given
        String memberId = "mstest102@gmail.com";
        List<Long> ids = queryRepository.selectComments(1L, memberId, PageRequest.of(0, 11), CommentSortCondition.HEART)
                .stream().map(OutCommentDTO::getCommentId)
                .collect(Collectors.toList());
        // when
        List<OutCommentHeartPeopleDto> result = queryRepository.selectCommentHeartPeopleByCommendIds(ids, memberId);
        // then
        for (OutCommentHeartPeopleDto outCommentHeartPeopleDto : result) {
            assertNotEquals(outCommentHeartPeopleDto, OutCommentHeartPeopleDto.builder().build());
            assertThat(outCommentHeartPeopleDto.getCommentId()).isIn(ids);
        }
    }
}