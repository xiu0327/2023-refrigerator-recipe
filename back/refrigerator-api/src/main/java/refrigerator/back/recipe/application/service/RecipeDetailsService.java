package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.application.domain.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.domain.dto.RecipeIngredientDto;
import refrigerator.back.recipe.application.mapper.RecipeBasicDataMapper;
import refrigerator.back.recipe.application.domain.dto.RecipeDto;
import refrigerator.back.recipe.application.mapper.RecipeCourseDataMapper;
import refrigerator.back.recipe.application.mapper.RecipeIngredientDataMapper;
import refrigerator.back.recipe.application.domain.RecipeIngredientAndCourseCollection;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailsUseCase;
import refrigerator.back.recipe.application.port.out.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeDetailsService implements FindRecipeDetailsUseCase {

    // --- recipeMapper ---
    private final RecipeBasicDataMapper recipeMapper;
    private final RecipeIngredientDataMapper ingredientMapper;
    private final RecipeCourseDataMapper coursesMapper;

    // --- port ---
    private final CheckMemberBookmarkedStatusPort bookmarkPort;
    private final GetRecipeBasicsDataPort recipePort;
    private final GetRecipeIngredientAndCourseDataPort recipeOtherDataPort;
    private final GetMyIngredientDataPort myIngredientPort;
    private final AddRecipeViewsPort addRecipeViewsPort;

    @Override
    @Transactional
    public RecipeDto findRecipeDetails(Long recipeId, String memberId, boolean isViewed) {
        RecipeIngredientAndCourseCollection otherCollection = recipeOtherDataPort.getData(recipeId);
        List<RecipeIngredientDto> ingredients = otherCollection.mappingIngredient(ingredientMapper, myIngredientPort.getMyIngredients(memberId));
        List<RecipeCourseDto> courses = otherCollection.mappingCourse(coursesMapper);
        RecipeDto result = recipeMapper.toInRecipeDto(
                recipePort.getData(recipeId),
                ingredients,
                courses,
                bookmarkPort.getStatus(recipeId, memberId)
        );
        if (!isViewed){
            addRecipeViewsPort.addViews(recipeId);
        }
        return result;
    }
}
