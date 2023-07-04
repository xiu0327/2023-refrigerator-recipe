package refrigerator.back.comment.application.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.comment.outbound.dto.OutCommentDTO;
import refrigerator.back.comment.outbound.mapper.OutCommentMapper;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.dto.InCommentDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    OutCommentMapper outCommentMapper = Mappers.getMapper(OutCommentMapper.class);
    CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    @DisplayName("outDto -> domainDto -> inDto: 현재 로그인한 사용자가 좋아요를 누른 댓글일 때")
    void toInCommentDtoByLikedPeople() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2024, 1, 1, 1, 1);
        OutCommentDTO outCommentDTO = new OutCommentDTO(
                1L,
                "nickname",
                2,
                createDateTime,
                false,
                "content",
                "email");
        // when
        CommentDto commentDTO = outCommentMapper.toCommentDto(outCommentDTO);
        InCommentDto inCommentDTO = commentMapper.toInCommentDto(commentDTO, "2시간 전", 1L);
        // then
        assertNotNull(inCommentDTO.getLikedPeopleInfo());
        assertTrue(inCommentDTO.getLikedPeopleInfo().getIsLiked());
        assertEquals(1L, inCommentDTO.getLikedPeopleInfo().getPeopleNo());
    }

    @Test
    @DisplayName("outDto -> domainDto -> inDto: 현재 로그인한 사용자가 좋아요를 누르지 않은 댓글일 때")
    void toInCommentDtoByNotLikedPeople() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2024, 1, 1, 1, 1);
        OutCommentDTO outCommentDTO = new OutCommentDTO(
                1L,
                "nickname",
                2,
                createDateTime,
                false,
                "content",
                "email");
        // when
        CommentDto commentDTO = outCommentMapper.toCommentDto(outCommentDTO);
        InCommentDto inCommentDTO = commentMapper.toInCommentDto(commentDTO, "2시간 전", null);
        // then
        assertNotNull(inCommentDTO.getLikedPeopleInfo());
        assertFalse(inCommentDTO.getLikedPeopleInfo().getIsLiked());
        assertNull(inCommentDTO.getLikedPeopleInfo().getPeopleNo());
    }
}