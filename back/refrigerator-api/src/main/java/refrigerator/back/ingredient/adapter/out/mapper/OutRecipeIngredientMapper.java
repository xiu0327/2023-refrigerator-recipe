package refrigerator.back.ingredient.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.out.dto.OutRecipeIngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;


@Mapper(componentModel = "spring")
public interface OutRecipeIngredientMapper {

    OutRecipeIngredientMapper INSTANCE = Mappers.getMapper(OutRecipeIngredientMapper.class);
    RecipeIngredientDto toRecipeIngredientDto(OutRecipeIngredientDTO dto);
}
