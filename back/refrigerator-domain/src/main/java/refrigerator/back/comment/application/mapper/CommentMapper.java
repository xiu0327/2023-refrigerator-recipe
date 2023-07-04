package refrigerator.back.comment.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.dto.InCommentDto;
import refrigerator.back.comment.application.dto.InCommentHeartPeopleDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    refrigerator.back.comment.application.mapper.CommentMapper INSTANCE =
            Mappers.getMapper(refrigerator.back.comment.application.mapper.CommentMapper.class);

    @Mapping(target = "likedPeopleInfo", source = "peopleId", qualifiedByName = "isLiked")
    InCommentDto toInCommentDto(CommentDto dto, String date, Object peopleId);

    @Named("isLiked")
    static InCommentHeartPeopleDto isLiked(Object peopleId){
        if (peopleId != null){
            return new InCommentHeartPeopleDto(true, peopleId.toString());
        }
        return new InCommentHeartPeopleDto(false, null);
    }
}
