package refrigerator.back.comment.application.port.in;

import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;

import java.util.List;

public interface FindCommentListUseCase {
    List<InCommentDTO> findComments(Long recipeId, int page, int size);
}
