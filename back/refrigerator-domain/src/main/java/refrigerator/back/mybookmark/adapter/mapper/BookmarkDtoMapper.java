package refrigerator.back.mybookmark.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import refrigerator.back.mybookmark.application.dto.InBookmarkDTO;
import refrigerator.back.mybookmark.application.dto.InBookmarkPreviewDTO;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkDTO;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkPreviewDTO;

@Mapper(componentModel = "spring")
public interface BookmarkDtoMapper {

    BookmarkDtoMapper INSTANCE = Mappers.getMapper(BookmarkDtoMapper.class);

    @Mapping(source = "dto.bookmarkId", target = "bookmarkID")
    InBookmarkPreviewDTO toBookmarkPreviewDTO(OutBookmarkPreviewDTO dto);

    @Mappings({
            @Mapping(source = "dto.bookmarkId", target = "bookmarkID"),
            @Mapping(source = "dto.recipeId", target = "recipeID"),
            @Mapping(source = "dto.recipeImage", target = "image"),
            @Mapping(source = "dto.score", target = "scoreAvg")
    })
    InBookmarkDTO toBookmarkDTO(OutBookmarkDTO dto);
}
