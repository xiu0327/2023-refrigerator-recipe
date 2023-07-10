package refrigerator.back.recipe_search.adapter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe_search.application.dto.InRecipeSearchDto;
import refrigerator.back.recipe_search.adapter.out.dto.OutRecipeDto;

@Mapper(componentModel = "spring")
public interface RecipeSearchDataMapper {

    RecipeSearchDataMapper INSTANCE = Mappers.getMapper(RecipeSearchDataMapper.class);

    /* outbound adapter -> inbound adapter */
//    @Mapping(source = "dto.myScore", target = "scoreAvg")
    InRecipeSearchDto toInRecipeSearchDto(OutRecipeDto dto);
}
