package refrigerator.back.ingredient.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;


@Mapper(componentModel = "spring")
public interface OutIngredientMapper {

    OutIngredientMapper INSTANCE = Mappers.getMapper(OutIngredientMapper.class);

    IngredientDetailDTO toIngredientDetailDto(OutIngredientDetailDTO outIngredientDetailDTO);

    IngredientDTO toIngredientDto(OutIngredientDTO outIngredientDTO);

}
