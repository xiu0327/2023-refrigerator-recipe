package refrigerator.back.recipe.adapter.out.repository;

public interface RecipeRepository {
    void updateRecipeViews(Long recipeID);
    void updateRecipeScore(Long recipeID, double score, int person);
    void updateBookmarkScore(Long recipeID, int value);
}
