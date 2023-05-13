package refrigerator.back.comment.application.port.in.comment;

import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;

import java.util.List;

public interface FindMyCommentsUseCase {
    List<InCommentDTO> findMyComments(String memberId, Long recipeId);
}
