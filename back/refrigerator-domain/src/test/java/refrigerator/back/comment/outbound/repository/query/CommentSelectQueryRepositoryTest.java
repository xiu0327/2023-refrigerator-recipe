package refrigerator.back.comment.outbound.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.outbound.dto.OutCommentDTO;
import refrigerator.back.comment.outbound.dto.OutCommentDtoCollection;
import refrigerator.back.comment.outbound.dto.OutCommentHeartPeopleDto;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.outbound.mapper.OutCommentMapper;
import refrigerator.back.global.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, CommentSelectQueryRepository.class})
@TestDataInit(value = {"/comment.sql", "/member.sql"})
@Slf4j
class CommentSelectQueryRepositoryTest {

    @Autowired CommentSelectQueryRepository queryRepository;
    OutCommentMapper mapper = Mappers.getMapper(OutCommentMapper.class);

    @Test
    @DisplayName("댓글 미리 보기 조회 성공 테스트")
    void selectPreviewComments() {
        int size = 3;
        OutCommentDtoCollection collection = queryRepository.selectPreviewComments(1L, PageRequest.of(0, size));
        assertEquals(size, collection.getSize());
        assertTrue(collection.isExist());
    }

    @Test
    @DisplayName("댓글 전체 개수 구하기")
    void selectCommentsCount(){
        Long result = queryRepository.selectCommentsCount(1L);
        assertEquals(11, result);
    }

    @Test
    @DisplayName("댓글 전체 하트순 조회 성공 테스트")
    void selectCommentsByHeartCountSuccessTest() {
        String memberId = "mstest102@gmail.com";
        int size = 5;
        OutCommentDtoCollection collection = queryRepository.selectComments(1L, memberId, PageRequest.of(0, size), CommentSortCondition.HEART);
        assertEquals(size, collection.getSize());
        assertTrue(collection.isExist());
    }

    @Test
    @DisplayName("댓글 전체 최신순 조회 성공 테스트")
    void selectCommentsByDateSuccessTest() {
        // given
        String memberId = "mstest102@gmail.com";
        int size = 5;
        // when
        OutCommentDtoCollection collection = queryRepository.selectComments(1L, memberId, PageRequest.of(0, size), CommentSortCondition.DATE);
        // then
        assertEquals(size, collection.getSize());
        assertTrue(collection.isExist());
    }

    @Test
    @DisplayName("내가 쓴 댓글 조회하기 성공 테스트")
    void selectMyComments() {
        // given
        String memberId = "jktest101@gmail.com";
        // when
        OutCommentDtoCollection collection = queryRepository.selectMyComments(memberId, 1L);
        // then
        assertTrue(collection.isExist());
    }

//    @Test
//    @DisplayName("조회된 댓글 중 내가 하트를 누른 댓글 찾기 성공 테스트")
//    void selectCommentHeartPeopleByCommendIds() {
//        // given
//        String memberId = "mstest102@gmail.com";
//        List<Long> ids = Arrays.asList(1L, 4L, 6L, 8L);
//        // when
//        List<OutCommentHeartPeopleDto> result = queryRepository(ids, memberId);
//        // then
//        for (OutCommentHeartPeopleDto outCommentHeartPeopleDto : result) {
//            assertNotEquals(outCommentHeartPeopleDto, OutCommentHeartPeopleDto.builder().build());
//            assertThat(outCommentHeartPeopleDto.getCommentId()).isIn(ids);
//        }
//    }
}