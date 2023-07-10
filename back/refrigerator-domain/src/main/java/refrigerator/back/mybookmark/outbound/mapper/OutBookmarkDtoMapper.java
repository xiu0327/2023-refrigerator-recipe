package refrigerator.back.mybookmark.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.mybookmark.outbound.dto.OutBookmarkDto;

@Mapper(componentModel = "spring")
public interface OutBookmarkDtoMapper {

    OutBookmarkDtoMapper INSTANCE = Mappers.getMapper(OutBookmarkDtoMapper.class);


}
