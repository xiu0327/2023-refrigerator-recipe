package refrigerator.back.comment.application.port.in;

public interface DeleteCommentUseCase {
    Long delete(String memberId, Long commentId);
}
