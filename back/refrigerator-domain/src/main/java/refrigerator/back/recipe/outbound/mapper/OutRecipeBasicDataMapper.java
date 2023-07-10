package refrigerator.back.recipe.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.outbound.dto.OutRecipeDto;
import refrigerator.back.recipe.application.dto.RecipeDomainDto;

@Mapper(componentModel = "spring")
public interface OutRecipeBasicDataMapper {

    OutRecipeBasicDataMapper INSTANCE = Mappers.getMapper(OutRecipeBasicDataMapper.class);

    RecipeDomainDto toRecipeDomainDto(OutRecipeDto dto);
}
