package refrigerator.back.comment.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.comment.adapter.dto.OutCommentDTO;
import refrigerator.back.comment.application.dto.CommentDTO;


@Mapper(componentModel = "spring")
public interface OutCommentMapper {

    OutCommentMapper INSTANCE = Mappers.getMapper(OutCommentMapper.class);

    CommentDTO toCommentDto(OutCommentDTO dto);
}
