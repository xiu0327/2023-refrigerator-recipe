package refrigerator.back.recipe_search.application.port.out;


import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.application.domain.RecipeSearchDataCollection;

public interface GetRecipeSearchDataPort {
    RecipeSearchDataCollection search(RecipeSearchCondition condition, int page, int size);
    RecipeSearchDataCollection normalSearch(int page, int size);
}
