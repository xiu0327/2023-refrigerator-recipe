package refrigerator.back.myscore.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScorePreviewDTO;
import refrigerator.back.myscore.adapter.out.dto.OutMyScoreDTO;
import refrigerator.back.myscore.adapter.out.dto.OutMyScorePreviewDTO;

@Mapper(componentModel = "spring")
public interface MyScoreDtoMapper {

    MyScoreDtoMapper INSTANCE = Mappers.getMapper(MyScoreDtoMapper.class);

    InMyScoreDTO toInMyScoreDto(OutMyScoreDTO dto, Double scoreAvg);
    InMyScorePreviewDTO toInMyScorePreviewDto(OutMyScorePreviewDTO dto);
}
