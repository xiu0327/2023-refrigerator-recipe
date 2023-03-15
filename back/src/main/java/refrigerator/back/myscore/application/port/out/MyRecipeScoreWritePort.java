package refrigerator.back.myscore.application.port.out;

public interface MyRecipeScoreWritePort {
    void create(String memberID, Long recipeID, double score);
    void update(Long scoreID, double newScore);
}
