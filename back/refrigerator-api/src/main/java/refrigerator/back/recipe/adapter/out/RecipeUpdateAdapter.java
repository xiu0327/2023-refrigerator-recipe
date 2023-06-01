package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.repository.RecipeRepository;
import refrigerator.back.recipe.application.port.out.AddRecipeScorePort;
import refrigerator.back.recipe.application.port.out.AddRecipeViewsPort;
import refrigerator.back.recipe.application.port.out.UpdateRecipeBookmarkPort;


@Repository
@RequiredArgsConstructor
public class RecipeUpdateAdapter implements AddRecipeViewsPort, AddRecipeScorePort, UpdateRecipeBookmarkPort {

    private final RecipeRepository recipeRepository;

    @Override
    public void addViews(Long recipeID) {
        recipeRepository.updateRecipeViews(recipeID);
    }

    @Override
    public void addScore(Long recipeID, double score, int person) {
        recipeRepository.updateRecipeScore(recipeID, score, person);
    }

    @Override
    public void addBookmark(Long recipeID) {
        recipeRepository.updateBookmarkScore(recipeID, 1);
    }

    @Override
    public void removeBookmark(Long recipeID) {
        recipeRepository.updateBookmarkScore(recipeID, -1);
    }
}
