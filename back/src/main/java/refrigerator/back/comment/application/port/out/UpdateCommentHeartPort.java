package refrigerator.back.comment.application.port.out;

public interface UpdateCommentHeartPort {
    void add(Long commentId);
    void reduce(Long commentId);
}
