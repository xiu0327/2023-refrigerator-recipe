package refrigerator.back.comment.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;
import refrigerator.back.comment.adapter.out.dto.OutCommentDTO;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    InCommentDTO toInCommentDto(OutCommentDTO dto);

}
