package refrigerator.back.mybookmark.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkDTO;
import refrigerator.back.mybookmark.adapter.in.dto.InBookmarkPreviewDTO;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkDTO;
import refrigerator.back.mybookmark.adapter.out.dto.OutBookmarkPreviewDTO;

@Mapper(componentModel = "spring")
public interface BookmarkDtoMapper {
    BookmarkDtoMapper INSTANCE = Mappers.getMapper(BookmarkDtoMapper.class);

    InBookmarkPreviewDTO toBookmarkPreviewDTO(OutBookmarkPreviewDTO dto);
    InBookmarkDTO toBookmarkDTO(OutBookmarkDTO dto, Double scoreAvg);
}
