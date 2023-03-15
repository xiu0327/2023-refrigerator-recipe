package refrigerator.back.myscore.application.port.in;


public interface AssessMyRecipeScoreUseCase {
    Long assessRecipeScore(String memberID, Long recipeID, double score);
}
