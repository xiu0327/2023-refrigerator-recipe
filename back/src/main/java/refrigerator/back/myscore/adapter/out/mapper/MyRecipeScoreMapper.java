package refrigerator.back.myscore.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScoreMappingDTO;
import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;

@Mapper(componentModel = "spring")
public interface MyRecipeScoreMapper {

    MyRecipeScoreMapper INSTANCE = Mappers.getMapper(MyRecipeScoreMapper.class);

    MyRecipeScoreDomain toDomain(MyRecipeScore entity);

    MyRecipeScore toEntity(MyRecipeScoreDomain domain);

    MyRecipeScoreDomain dtoToDomain(MyRecipeScoreMappingDTO dto);
}
