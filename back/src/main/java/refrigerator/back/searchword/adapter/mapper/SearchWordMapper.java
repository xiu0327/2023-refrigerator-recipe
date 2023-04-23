package refrigerator.back.searchword.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import refrigerator.back.searchword.adapter.out.dto.OutIngredientDTO;
import refrigerator.back.searchword.application.domain.Ingredient;

@Mapper(componentModel = "spring")
public interface SearchWordMapper {

    SearchWordMapper INSTANCE = Mappers.getMapper(SearchWordMapper.class);

    @Mapping(source = "date", target = "expirationDate")
    Ingredient toIngredient(OutIngredientDTO dto);
}
