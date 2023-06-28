package refrigerator.back.comment.application.port.out.trash;



import refrigerator.back.comment.application.dto.CommentDTO;
import refrigerator.back.comment.application.dto.CommentListAndCountDTO;

import java.util.List;

public interface CommentReadPort {
    CommentListAndCountDTO<CommentDTO> findCommentPreviewList(Long recipeId, int size);
    List<CommentDTO> findCommentListByHeart(Long recipeId, String memberId, int page, int size);
    List<CommentDTO> findCommentListByDate(Long recipeId, String memberId, int page, int size);
}
