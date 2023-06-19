package refrigerator.back.comment.application.port.out;


import refrigerator.back.comment.application.dto.CommentDTO;

import java.util.List;

public interface FindMyCommentListPort {
    List<CommentDTO> findMyComments(String memberId, Long recipeId);
}
