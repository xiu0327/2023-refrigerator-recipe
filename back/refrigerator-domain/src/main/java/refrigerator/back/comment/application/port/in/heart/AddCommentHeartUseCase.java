package refrigerator.back.comment.application.port.in.heart;

public interface AddCommentHeartUseCase {
    void addHeart(Long commentId, String memberId);
}
