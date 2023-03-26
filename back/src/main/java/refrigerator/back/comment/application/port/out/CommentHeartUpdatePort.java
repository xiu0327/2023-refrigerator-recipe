package refrigerator.back.comment.application.port.out;

public interface CommentHeartUpdatePort {
    void add(Long commentId);
    void reduce(Long commentId);
}
