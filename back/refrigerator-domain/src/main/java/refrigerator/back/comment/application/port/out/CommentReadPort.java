package refrigerator.back.comment.application.port.out;



import refrigerator.back.comment.application.domain.CommentDto;
import refrigerator.back.comment.application.domain.CommentListDto;

import java.util.List;

public interface CommentReadPort {
    CommentListDto findCommentPreviewList(Long recipeId, int size);
    List<CommentDto> findCommentListByHeart(Long recipeId, String memberId, int page, int size);
    List<CommentDto> findCommentListByDate(Long recipeId, String memberId, int page, int size);
}
