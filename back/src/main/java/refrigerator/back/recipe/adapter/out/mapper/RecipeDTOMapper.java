package refrigerator.back.recipe.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.adapter.out.dto.RecipeMappingDTO;
import refrigerator.back.recipe.adapter.out.dto.RecipeJoinDataMappingDTO;
import refrigerator.back.recipe.adapter.out.entity.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeDTOMapper {
    RecipeDTOMapper INSTANCE = Mappers.getMapper(RecipeDTOMapper.class);

    RecipeMappingDTO entityToDto(Recipe recipe, RecipeJoinDataMappingDTO oneToOneDto);

}
