package refrigerator.back.comment.adapter.out.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import refrigerator.back.comment.adapter.out.CommentSortCondition;
import refrigerator.back.comment.adapter.out.dto.OutCommentDTO;


import java.util.List;

public interface CommentQueryRepository {
    Page<OutCommentDTO> findCommentPreviewList(Long recipeId, Pageable page);
    List<OutCommentDTO> findCommentList(Long recipeId, String memberId, Pageable page, CommentSortCondition sortCondition);
    List<OutCommentDTO> findMyCommentList(String memberId, Long recipeId);
}
