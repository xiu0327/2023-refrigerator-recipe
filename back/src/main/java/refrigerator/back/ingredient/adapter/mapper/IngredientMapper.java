package refrigerator.back.ingredient.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.adapter.in.dto.*;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    @Mappings({
            @Mapping(source = "ingredient.id", target = "ingredientID"),
            @Mapping(source = "ingredient.capacity", target = "volume"),
            @Mapping(source = "ingredient.storageMethod", target = "storage"),
            @Mapping(source = "ingredient.capacityUnit", target = "unit")
    })
    IngredientDetailResponseDTO toIngredientDetailDto(Ingredient ingredient);

    @Mapping(source = "ingredient.id", target = "ingredientID")
    IngredientResponseDTO toIngredientDto(Ingredient ingredient);

    @Mapping(source = "ingredient.id", target = "ingredientID")
    IngredientRegisteredResponseDTO toIngredientRegisteredResponseDTO(RegisteredIngredient ingredient);

    IngredientSearchCondition toIngredientSearchCondition(String storage, boolean deadline, String email);

}
