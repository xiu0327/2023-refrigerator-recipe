package refrigerator.back.myscore.application.port.in;


public interface CookingUseCase {
    Long firstCooking(String memberId, Long recipeId, Double score);
    void retryCooking(Long scoreId, Double score);
}
