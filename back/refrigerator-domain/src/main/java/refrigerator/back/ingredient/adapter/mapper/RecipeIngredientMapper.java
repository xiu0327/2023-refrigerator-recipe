package refrigerator.back.ingredient.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.out.dto.OutRecipeIngredientDTO;
import refrigerator.back.ingredient.application.domain.RecipeIngredientDto;


@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {

    RecipeIngredientMapper INSTANCE = Mappers.getMapper(RecipeIngredientMapper.class);
    RecipeIngredientDto toRecipeIngredientDto(OutRecipeIngredientDTO dto);
}
