package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.application.port.in.FindRecipeCourseUseCase;
import refrigerator.back.recipe.application.port.in.FindRecipeListUseCase;
import refrigerator.back.recipe.application.port.out.ReadRecipePort;
import refrigerator.back.recipe.application.domain.entity.RecipeCourseDomain;
import refrigerator.back.recipe.application.domain.entity.RecipeDomain;
import refrigerator.back.recipe.application.port.in.FindRecipeDetailUseCase;
import refrigerator.back.recipe.application.port.out.AddRecipeViewsPort;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService implements FindRecipeListUseCase, FindRecipeDetailUseCase, FindRecipeCourseUseCase {

    private final ReadRecipePort recipeReadPort;
    private final AddRecipeViewsPort addRecipeViewsPort;

    @Override
    @Transactional
    public RecipeDomain getRecipe(Long recipeID, boolean isViewed) {
        try{
            RecipeDomain recipe = recipeReadPort.getRecipeDetails(recipeID);
            if (!isViewed){
                addRecipeViewsPort.addViews(recipeID);
            }
            return recipe;
        }catch (RuntimeException e){
            throw new BusinessException(RecipeExceptionType.NOT_FOUND_RECIPE);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDomain> getRecipeList(int page, int size) {
        return recipeReadPort.getRecipeList(page, size)
                .stream().map(RecipeDomain::calculateScoreAvg)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeCourseDomain> getRecipeCourse(Long recipeID) {
        return recipeReadPort.getRecipeCourse(recipeID);
    }
}
