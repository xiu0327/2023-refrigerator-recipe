package refrigerator.back.recipe.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import refrigerator.back.recipe.adapter.out.dto.*;
import refrigerator.back.recipe.adapter.in.dto.*;
import refrigerator.back.recipe.application.domain.entity.*;

@Mapper(componentModel = "spring")
public interface RecipeDtoMapper {

    RecipeDtoMapper INSTANCE = Mappers.getMapper(RecipeDtoMapper.class);

    RecipeDetails toRecipeDetails(OutRecipeDetailDTO dto);
    RecipeSearchCondition toRecipeSearchCondition(InRecipeSearchRequestDTO dto);
    InRecipeDTO toInRecipeDto(OutRecipeDTO dto, Double scoreAvg);
    InRecipeCourseDTO toInRecipeCourseDto(RecipeCourse recipeCourse);
    InRecipeDetailDTO toInRecipeDetailsDto(Recipe recipe, RecipeDetails details, Double scoreAvg);
    InRecipeIngredientDTO toInRecipeIngredientDto(RecipeIngredient ingredient);

    @Mappings({
            @Mapping(source = "recipeScore", target = "scoreAvg"),
            @Mapping(source = "dto.recipeId", target = "recipeID"),
            @Mapping(source = "dto.recipeImage", target = "image")
    })
    InRecipeRecommendDTO toInRecipeRecommendDto(OutRecipeRecommendDTO dto, Double recipeScore);

    @Mapping(source = "dto.id", target = "recipeIngredientId")
    InRecipeIngredientVolumeDTO toInRecipeIngredientVolumeDTO(OutRecipeIngredientVolumeDTO dto);

}
