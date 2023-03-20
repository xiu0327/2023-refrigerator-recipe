package refrigerator.back.myscore.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.myscore.adapter.in.dto.MyRecipeScoreDTO;
import refrigerator.back.myscore.adapter.in.dto.MyRecipeScoreListResponseDTO;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;

@Mapper(componentModel = "spring")
public interface MyRecipeScoreInputMapper {
    MyRecipeScoreInputMapper INSTANCE = Mappers.getMapper(MyRecipeScoreInputMapper.class);

    MyRecipeScoreDTO toScoreDto(MyRecipeScoreDomain domain);
}
