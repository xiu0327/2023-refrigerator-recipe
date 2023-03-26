package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;
import refrigerator.back.comment.adapter.in.dto.response.InCommentListDTO;
import refrigerator.back.comment.application.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentReadPort {
    InCommentListDTO findCommentPreviewList(Long recipeId, int size);
    List<InCommentDTO> findCommentList(Long recipeId, int page, int size);
}
