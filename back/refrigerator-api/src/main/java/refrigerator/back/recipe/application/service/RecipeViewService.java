package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.application.port.in.AddRecipeViewCountUseCase;
import refrigerator.back.recipe.application.port.out.AddRecipeViewsPort;

@Service
@RequiredArgsConstructor
public class RecipeViewService implements AddRecipeViewCountUseCase {

    private final AddRecipeViewsPort addRecipeViewsPort;

    @Override
    @Transactional
    public void add(boolean isViewed, Long recipeId) {
        if (!isViewed){
            addRecipeViewsPort.addViews(recipeId);
        }
    }
}
