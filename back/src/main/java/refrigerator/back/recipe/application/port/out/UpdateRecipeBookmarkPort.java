package refrigerator.back.recipe.application.port.out;

public interface UpdateRecipeBookmarkPort {
    void addBookmark(Long recipeID);
    void removeBookmark(Long recipeID);
}
