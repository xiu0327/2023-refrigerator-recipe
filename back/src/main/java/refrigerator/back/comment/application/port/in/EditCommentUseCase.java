package refrigerator.back.comment.application.port.in;

public interface EditCommentUseCase {
    Long edit(String memberId, Long commentId, String content);
}
