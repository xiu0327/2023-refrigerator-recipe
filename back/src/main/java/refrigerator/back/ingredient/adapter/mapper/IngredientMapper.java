package refrigerator.back.ingredient.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.in.dto.response.IngredientResponseDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientDetailResponseDTO;
import refrigerator.back.ingredient.adapter.out.dto.OutIngredientResponseDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScoreDTO;
import refrigerator.back.myscore.adapter.in.dto.response.InMyScorePreviewDTO;
import refrigerator.back.myscore.adapter.out.dto.OutMyScoreDTO;
import refrigerator.back.myscore.adapter.out.dto.OutMyScorePreviewDTO;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDetailResponseDTO toIngredientDetailDto(OutIngredientDetailResponseDTO OutDTO);
    IngredientResponseDTO toIngredientDto(OutIngredientResponseDTO OutDTO);
}
