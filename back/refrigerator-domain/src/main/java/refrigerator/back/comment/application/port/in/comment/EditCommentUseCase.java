package refrigerator.back.comment.application.port.in.comment;

public interface EditCommentUseCase {
    Long edit(String memberId, Long commentId, String content);
}
