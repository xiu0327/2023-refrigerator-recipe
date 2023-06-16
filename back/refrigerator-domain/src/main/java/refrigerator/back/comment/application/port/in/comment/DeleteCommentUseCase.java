package refrigerator.back.comment.application.port.in.comment;

public interface DeleteCommentUseCase {
    Long delete(String memberId, Long commentId);
}
