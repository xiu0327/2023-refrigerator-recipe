package refrigerator.back.comment.application.port.in;

public interface WriteCommentUseCase {
    void write(Long recipeId, String memberId, String content);
}
