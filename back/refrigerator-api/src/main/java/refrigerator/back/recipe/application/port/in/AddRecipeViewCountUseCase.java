package refrigerator.back.recipe.application.port.in;

public interface AddRecipeViewCountUseCase {
    void add(boolean isViewed, Long recipeId);
}
