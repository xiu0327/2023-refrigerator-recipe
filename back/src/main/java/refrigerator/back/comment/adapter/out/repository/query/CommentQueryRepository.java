package refrigerator.back.comment.adapter.out.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import refrigerator.back.comment.adapter.out.dto.OutCommentDTO;
import refrigerator.back.comment.application.domain.CommentHeart;

import java.util.List;
import java.util.Optional;

public interface CommentQueryRepository {
    Page<OutCommentDTO> findCommentPreviewList(Long recipeId, Pageable page);
    List<OutCommentDTO> findCommentList(Long recipeId, Pageable page);
    void updateCommentHeartCount(Long commentId, int value);
    Long persistCommentHeart(CommentHeart commentHeart);
}
