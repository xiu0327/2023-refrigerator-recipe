package refrigerator.back.myscore.application.port.in;

public interface FindMyRecipeScoreUseCase {
    double getMyScore(String memberID, Long recipeID);
}
