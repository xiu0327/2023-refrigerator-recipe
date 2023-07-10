package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.recipe.outbound.repository.query.RecipeUpdateQueryRepository;
import refrigerator.back.recipe.application.port.out.AddRecipeViewsPort;
import refrigerator.back.recipe.application.port.out.UpdateRecipeBookmarkPort;


@Component
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
