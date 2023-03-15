package refrigerator.back.recipe.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.mapper.RecipeMapper;
import refrigerator.back.recipe.adapter.out.repository.RecipeRepository;
import refrigerator.back.recipe.application.port.out.AddRecipeScorePort;
import refrigerator.back.recipe.application.port.out.AddRecipeViewsPort;

@Repository
@RequiredArgsConstructor
public class RecipeUpdateAdapter implements AddRecipeViewsPort, AddRecipeScorePort {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public void addViews(Long recipeID) {
        recipeRepository.updateRecipeViews(recipeID);
    }

    @Override
    public void addScore(Long recipeID, double score) {
        recipeRepository.updateRecipeScore(recipeID, score);
    }
}
