package refrigerator.back.ingredient.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.in.dto.*;
import refrigerator.back.recipe.adapter.out.dto.OutRecipeIngredientVolumeDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDetailResponseDTO toIngredientDetailDto(Ingredient ingredient, Long remainDays);

    IngredientResponseDTO toIngredientDto(Ingredient ingredient, Long remainDays);

    IngredientRegisteredResponseDTO toIngredientRegisteredResponseDTO(RegisteredIngredient ingredient);

    IngredientSearchCondition toIngredientSearchCondition(IngredientLookUpRequestDTO dto, String email);

    RecipeIngredientVolumeDTO toIngredientVolumeByRecipe(OutRecipeIngredientVolumeDTO dto);
}
