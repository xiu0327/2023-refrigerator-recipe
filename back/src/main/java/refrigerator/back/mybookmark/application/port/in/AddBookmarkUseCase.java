package refrigerator.back.mybookmark.application.port.in;

public interface AddBookmarkUseCase {
    Long add(String memberId, Long recipeId);
}
