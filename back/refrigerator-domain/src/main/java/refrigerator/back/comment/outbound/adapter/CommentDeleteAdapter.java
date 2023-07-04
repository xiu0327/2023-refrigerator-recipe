package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.outbound.repository.query.CommentDeleteQueryRepository;
import refrigerator.back.comment.application.port.out.DeleteCommentPort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;

@Repository
@RequiredArgsConstructor
public class CommentDeleteAdapter implements DeleteCommentPort {

    private final CommentDeleteQueryRepository deleteQueryRepository;

    /**
     * 댓글 삭제
     * @param id 댓글 고유 식별자(id)
     */
    @Override
    public void delete(Long id) {
        long deleteComment = deleteQueryRepository.deleteComment(id);
        long deleteCommentHeart = deleteQueryRepository.deleteCommentHeart(id);
        if (deleteComment + deleteCommentHeart != 2){
            throw new BusinessException(CommentExceptionType.FAIL_DELETE_COMMENT);
        }
    }
}
