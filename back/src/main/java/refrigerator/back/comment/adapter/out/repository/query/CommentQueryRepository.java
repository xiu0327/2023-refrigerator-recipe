package refrigerator.back.comment.adapter.out.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import refrigerator.back.comment.adapter.out.dto.OutCommentDTO;
import refrigerator.back.comment.adapter.out.persistence.CommentSortCondition;
import refrigerator.back.comment.application.domain.CommentHeart;

import java.util.List;

public interface CommentQueryRepository {
    Page<OutCommentDTO> findCommentPreviewList(Long recipeId, Pageable page);
    List<OutCommentDTO> findCommentList(Long recipeId, Pageable page, CommentSortCondition sortCondition);
    long updateCommentHeartCount(Long commentId, int value);
    long deleteCommentHeartCount(Long commentId);
    Long persistCommentHeart(CommentHeart commentHeart);
}
