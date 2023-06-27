package refrigerator.back.ingredient.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.dto.OutIngredientDTO;
import refrigerator.back.ingredient.adapter.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;


@Mapper(componentModel = "spring")
public interface OutIngredientMapper {

    OutIngredientMapper INSTANCE = Mappers.getMapper(OutIngredientMapper.class);

    IngredientDetailDTO toIngredientDetailDto(OutIngredientDetailDTO outIngredientDetailDTO);

    IngredientDTO toIngredientDto(OutIngredientDTO outIngredientDTO);
}
