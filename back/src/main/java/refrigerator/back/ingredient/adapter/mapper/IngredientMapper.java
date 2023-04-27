package refrigerator.back.ingredient.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.in.dto.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientLookUpRequestDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientRegisteredResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.IngredientResponseDTO;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDetailResponseDTO toIngredientDetailDto(Ingredient ingredient);

    IngredientResponseDTO toIngredientDto(Ingredient ingredient);

    IngredientRegisteredResponseDTO toIngredientRegisteredResponseDTO(RegisteredIngredient ingredient);

    IngredientSearchCondition toIngredientSearchCondition(IngredientLookUpRequestDTO dto, String email);
}
