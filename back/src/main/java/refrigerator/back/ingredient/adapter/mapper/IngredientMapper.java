package refrigerator.back.ingredient.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientUnitResponseDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDetailResponseDTO toIngredientDetailDto(OutIngredientDetailDTO outIngredientDetailDTO);

    IngredientResponseDTO toIngredientDto(OutIngredientDTO outIngredientDTO);

    IngredientSearchCondition toIngredientSearchCondition(IngredientStorageType storage, Boolean deadline, String email);

    IngredientUnitResponseDTO toIngredientUnitResponseDTO(RegisteredIngredient ingredient);
}
