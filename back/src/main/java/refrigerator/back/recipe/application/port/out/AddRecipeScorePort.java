package refrigerator.back.recipe.application.port.out;

public interface AddRecipeScorePort {
    void addScore(Long recipeID, double score);
}
