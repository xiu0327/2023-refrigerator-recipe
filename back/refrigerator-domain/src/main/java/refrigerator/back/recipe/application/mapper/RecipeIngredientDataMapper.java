package refrigerator.back.recipe.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.application.domain.dto.RecipeIngredientDto;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;

@Mapper(componentModel = "spring")
public interface RecipeIngredientDataMapper {

    RecipeBasicDataMapper INSTANCE = Mappers.getMapper(RecipeBasicDataMapper.class);

    RecipeIngredientDto toInRecipeIngredientDto(RecipeIngredient ingredient, Boolean isOwned);

}
