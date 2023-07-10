package refrigerator.back.recipe.application.port.in;

public interface RecipeScoreModifyHandler {
    void addUp(Long recipeId, Double newScore);
    void renew(Long recipeId, Double oldScore, Double newScore);
}
