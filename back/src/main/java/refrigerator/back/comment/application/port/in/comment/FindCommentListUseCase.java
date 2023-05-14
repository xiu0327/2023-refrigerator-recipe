package refrigerator.back.comment.application.port.in.comment;

import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;

import java.util.List;

public interface FindCommentListUseCase {
    List<InCommentDTO> findCommentsByHeart(Long recipeId, String memberId, int page, int size);
    List<InCommentDTO> findCommentsByDate(Long recipeId, String memberId, int page, int size);
}
