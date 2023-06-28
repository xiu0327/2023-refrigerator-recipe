package refrigerator.back.comment.application.port.out.trash;


import refrigerator.back.comment.application.domain.CommentHeart;

public interface CreateCommentHeartPort {
    Long create(CommentHeart commentHeart);
}
