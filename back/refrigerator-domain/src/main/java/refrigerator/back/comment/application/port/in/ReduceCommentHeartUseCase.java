package refrigerator.back.comment.application.port.in;

public interface ReduceCommentHeartUseCase {
    void reduceHeart(Long commentId, String peopleId);
}
