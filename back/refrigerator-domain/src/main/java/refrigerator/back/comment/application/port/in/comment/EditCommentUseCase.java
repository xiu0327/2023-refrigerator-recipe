package refrigerator.back.comment.application.port.in.comment;

public interface EditCommentUseCase {
    void edit(String memberId, Long commentId, String content);
}
