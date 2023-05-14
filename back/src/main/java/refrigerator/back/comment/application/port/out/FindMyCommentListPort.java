package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.CommentDto;

import java.util.List;

public interface FindMyCommentListPort {
    List<CommentDto> findMyComments(String memberId, Long recipeId);
}
