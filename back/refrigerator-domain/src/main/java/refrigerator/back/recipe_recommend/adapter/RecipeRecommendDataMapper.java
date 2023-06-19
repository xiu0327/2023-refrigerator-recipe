package refrigerator.back.recipe_recommend.adapter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe_recommend.application.dto.InRecipeRecommendDTO;
import refrigerator.back.recipe_recommend.adapter.out.dto.OutRecipeRecommendDTO;

@Mapper(componentModel = "spring")
public interface RecipeRecommendDataMapper {

    RecipeRecommendDataMapper INSTANCE = Mappers.getMapper(RecipeRecommendDataMapper.class);

    @Mappings({
            @Mapping(source = "dto.score", target = "scoreAvg"),
            @Mapping(source = "dto.recipeId", target = "recipeID"),
            @Mapping(source = "dto.recipeImage", target = "image")
    })
    InRecipeRecommendDTO toInRecipeRecommendDto(OutRecipeRecommendDTO dto, Double match);
}
