package refrigerator.back.comment.application.port.out;


import refrigerator.back.comment.application.domain.CommentHeart;

public interface CreateCommentHeartPort {
    Long create(CommentHeart commentHeart);
}
