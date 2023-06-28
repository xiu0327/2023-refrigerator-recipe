package refrigerator.back.comment.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.adapter.repository.query.CommentUpdateQueryRepository;
import refrigerator.back.comment.application.port.out.ModifyCommentPort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class CommentModifyAdapter implements ModifyCommentPort {

    private final CommentUpdateQueryRepository updateQueryRepository;

    @Override
    public void modifyContent(Long id, String content, LocalDateTime now) {
        long result = updateQueryRepository.updateCommentToContent(id, content, now);
        if (result != 1){
            throw new BusinessException(CommentExceptionType.FAIL_MODIFY_COMMENT);
        }
    }

    @Override
    public void modifyHeartCount(Long id, int value) {
        updateQueryRepository.updateCommentToCount(id, value);
    }
}
