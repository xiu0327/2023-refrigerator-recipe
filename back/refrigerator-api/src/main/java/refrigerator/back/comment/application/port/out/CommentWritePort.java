package refrigerator.back.comment.application.port.out;


import refrigerator.back.comment.application.domain.Comment;

public interface CommentWritePort {
    Long persist(Comment comment);
}
