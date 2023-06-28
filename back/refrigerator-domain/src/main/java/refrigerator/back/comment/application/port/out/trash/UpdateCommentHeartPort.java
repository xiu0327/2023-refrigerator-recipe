package refrigerator.back.comment.application.port.out.trash;

public interface UpdateCommentHeartPort {
    void add(Long commentId);
    void reduce(Long commentId);
}
