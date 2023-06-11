package refrigerator.back.recipe.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientDto;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;

@Mapper(componentModel = "spring")
public interface RecipeIngredientDataMapper {

    RecipeBasicDataMapper INSTANCE = Mappers.getMapper(RecipeBasicDataMapper.class);

    InRecipeIngredientDto toInRecipeIngredientDto(RecipeIngredient ingredient, Boolean isOwned);

}
