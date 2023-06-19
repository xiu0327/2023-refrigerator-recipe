package refrigerator.back.comment.application.port.in.comment;


import refrigerator.back.comment.application.dto.CommentListAndCountDTO;
import refrigerator.back.comment.application.dto.InCommentDTO;

public interface FindCommentPreviewListUseCase {
    CommentListAndCountDTO<InCommentDTO> findCommentPreviews(Long recipeId, String memberId, int size);
}
