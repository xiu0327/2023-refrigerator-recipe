package refrigerator.back.recipe.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeDto;
import refrigerator.back.recipe.application.domain.dto.RecipeDomainDto;

@Mapper(componentModel = "spring")
public interface OutRecipeBasicDataMapper {

    OutRecipeBasicDataMapper INSTANCE = Mappers.getMapper(OutRecipeBasicDataMapper.class);

    RecipeDomainDto toRecipeDomainDto(OutRecipeDto dto);
}
