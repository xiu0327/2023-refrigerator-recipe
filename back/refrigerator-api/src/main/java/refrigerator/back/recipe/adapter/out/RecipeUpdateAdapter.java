package refrigerator.back.recipe.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.adapter.out.repository.RecipeUpdateQueryRepository;
import refrigerator.back.recipe.application.port.out.AddRecipeViewsPort;
import refrigerator.back.recipe.application.port.out.UpdateRecipeBookmarkPort;


@Repository
@RequiredArgsConstructor
public class RecipeUpdateAdapter implements AddRecipeViewsPort, UpdateRecipeBookmarkPort {

    private final RecipeUpdateQueryRepository repository;

    @Override
    public void addViews(Long recipeID) {
        repository.updateRecipeViews(recipeID);
    }

    @Override
    public void addBookmark(Long recipeID) {
        repository.updateBookmarkScore(recipeID, 1);
    }

    @Override
    public void removeBookmark(Long recipeID) {
        repository.updateBookmarkScore(recipeID, -1);
    }
}
