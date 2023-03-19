package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.application.domain.entity.RecipeDomain;

import java.util.List;

public interface FindRecipeListUseCase {
    List<RecipeDomain> getRecipeList(int page, int size);
}
