package refrigerator.back.comment.application.port.in.heart;

public interface AddCommentHeartUseCase {
    Long addHeart(String memberId, Long commentId);
}
