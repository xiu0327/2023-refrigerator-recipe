package refrigerator.back.mybookmark.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import refrigerator.back.mybookmark.application.dto.BookmarkDto;
import refrigerator.back.mybookmark.application.dto.BookmarkPreviewDto;
import refrigerator.back.mybookmark.outbound.dto.OutBookmarkDto;
import refrigerator.back.mybookmark.outbound.dto.OutBookmarkPreviewDto;

@Mapper(componentModel = "spring")
public interface BookmarkDtoMapper {

    BookmarkDtoMapper INSTANCE = Mappers.getMapper(BookmarkDtoMapper.class);

}
