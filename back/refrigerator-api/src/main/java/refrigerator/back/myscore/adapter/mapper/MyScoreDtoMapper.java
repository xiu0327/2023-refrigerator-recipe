package refrigerator.back.myscore.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScorePreviewDTO;
import refrigerator.back.myscore.adapter.out.dto.OutMyScoreDTO;
import refrigerator.back.myscore.adapter.out.dto.OutMyScorePreviewDTO;

@Mapper(componentModel = "spring")
public interface MyScoreDtoMapper {

    MyScoreDtoMapper INSTANCE = Mappers.getMapper(MyScoreDtoMapper.class);

    @Mappings({
            @Mapping(source = "dto.scoreId", target = "scoreID"),
            @Mapping(source = "dto.recipeId", target = "recipeID"),
            @Mapping(source = "dto.recipeImage", target = "image"),
            @Mapping(source = "dto.score", target = "scoreAvg"),
    })
    InMyScoreDTO toInMyScoreDto(OutMyScoreDTO dto);
    InMyScorePreviewDTO toInMyScorePreviewDto(OutMyScorePreviewDTO dto);
}
