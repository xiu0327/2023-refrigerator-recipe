package refrigerator.back.comment.application.port.in.comment;


import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.dto.InCommentDto;

import java.util.List;

public interface FindCommentsUseCase {
    List<InCommentDto> findComments(Long recipeId, String memberId, CommentSortCondition sortCondition, int page, int size);
    List<InCommentDto> findCommentsPreview(Long recipeId, String memberId, int size);
    List<InCommentDto> findMyComments(String memberId, Long recipeId);
}
