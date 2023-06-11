package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.adapter.in.dto.InRecipeCourseDto;
import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientDto;
import refrigerator.back.recipe.adapter.mapper.RecipeBasicDataMapper;
import refrigerator.back.recipe.adapter.in.dto.InRecipeDto;
import refrigerator.back.recipe.adapter.mapper.RecipeCourseDataMapper;
import refrigerator.back.recipe.adapter.mapper.RecipeIngredientDataMapper;
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

    @Override
    @Transactional(readOnly = true)
    public InRecipeDto findRecipeDetails(Long recipeId, String memberId) {
        RecipeIngredientAndCourseCollection otherCollection = recipeOtherDataPort.getData(recipeId);
        List<InRecipeIngredientDto> ingredients = otherCollection.mappingIngredient(ingredientMapper, myIngredientPort.getMyIngredients(memberId));
        List<InRecipeCourseDto> courses = otherCollection.mappingCourse(coursesMapper);
        return recipeMapper.toInRecipeDto(
                recipePort.getData(recipeId),
                ingredients,
                courses,
                bookmarkPort.getStatus(recipeId, memberId)
        );
    }
}
