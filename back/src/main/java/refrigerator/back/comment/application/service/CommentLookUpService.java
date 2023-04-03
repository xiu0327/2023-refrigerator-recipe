package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.in.dto.response.InCommentDTO;
import refrigerator.back.comment.adapter.in.dto.response.InCommentListDTO;
import refrigerator.back.comment.application.port.in.FindCommentListUseCase;
import refrigerator.back.comment.application.port.in.FindCommentPreviewListUseCase;
import refrigerator.back.comment.application.port.out.CommentReadPort;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLookUpService implements FindCommentListUseCase, FindCommentPreviewListUseCase {

    private final CommentReadPort commentReadPort;


    @Override
    public InCommentListDTO findCommentPreviews(Long recipeId, int size) {
        return commentReadPort.findCommentPreviewList(recipeId, size);
    }

    @Override
    public List<InCommentDTO> findCommentsByHeart(Long recipeId, int page, int size) {
        return commentReadPort.findCommentListByHeart(recipeId, page, size);
    }

    @Override
    public List<InCommentDTO> findCommentsByDate(Long recipeId, int page, int size) {
        return commentReadPort.findCommentListByDate(recipeId, page, size);
    }
}
