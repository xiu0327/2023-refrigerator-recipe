package refrigerator.back.comment.application.port.in;

public interface AddCommentHeartUseCase {
    void addHeart(Long commentId, String memberId);
}
