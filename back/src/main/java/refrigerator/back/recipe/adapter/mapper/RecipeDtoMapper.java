package refrigerator.back.recipe.adapter.mapper;

import org.mapstruct.Mapper;
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
    InRecipeRecommendDTO toInRecipeRecommendDto(OutRecipeRecommendDTO dto, Double recipeScore);
    InRecipeIngredientVolumeDTO toInRecipeIngredientVolumeDTO(OutRecipeIngredientVolumeDTO dto);

}
