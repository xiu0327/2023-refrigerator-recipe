package refrigerator.back.comment.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.comment.outbound.dto.OutCommentDto;
import refrigerator.back.comment.application.dto.CommentDto;


@Mapper(componentModel = "spring")
public interface OutCommentMapper {

    OutCommentMapper INSTANCE = Mappers.getMapper(OutCommentMapper.class);

    CommentDto toCommentDto(OutCommentDto dto);

}
