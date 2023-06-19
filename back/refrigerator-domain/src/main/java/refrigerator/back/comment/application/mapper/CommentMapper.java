package refrigerator.back.comment.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.comment.application.dto.CommentDTO;
import refrigerator.back.comment.application.dto.InCommentDTO;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    refrigerator.back.comment.application.mapper.CommentMapper INSTANCE =
            Mappers.getMapper(refrigerator.back.comment.application.mapper.CommentMapper.class);

    @Mapping(source = "dto.commentId", target = "commentID")
    InCommentDTO toInCommentDto(CommentDTO dto, String date, Boolean isMyComment);
}
