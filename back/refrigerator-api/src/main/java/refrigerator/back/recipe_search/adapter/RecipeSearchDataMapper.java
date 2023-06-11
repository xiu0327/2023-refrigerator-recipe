package refrigerator.back.recipe_search.adapter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchDto;
import refrigerator.back.recipe_search.adapter.out.dto.OutRecipeDto;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.adapter.in.dto.InRecipeSearchConditionDto;

@Mapper(componentModel = "spring")
public interface RecipeSearchDataMapper {

    RecipeSearchDataMapper INSTANCE = Mappers.getMapper(RecipeSearchDataMapper.class);

    /* outbound adapter -> domain */
    RecipeSearchCondition toRecipeSearchCondition(InRecipeSearchConditionDto dto);

    /* outbound adapter -> inbound adapter */
    @Mapping(source = "dto.score", target = "scoreAvg")
    InRecipeSearchDto toInRecipeSearchDto(OutRecipeDto dto);
}
