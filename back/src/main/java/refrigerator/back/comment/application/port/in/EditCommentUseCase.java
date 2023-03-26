package refrigerator.back.comment.application.port.in;

public interface EditCommentUseCase {
    Long edit(Long commentId, String content);
}
