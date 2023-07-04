package refrigerator.back.comment.application.port.in.heart;

public interface ReduceCommentHeartUseCase {
    void reduceHeart(Long commentId, String peopleId);
}
