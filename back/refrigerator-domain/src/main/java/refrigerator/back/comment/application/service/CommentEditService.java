package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.port.in.comment.EditCommentUseCase;
import refrigerator.back.comment.application.port.out.ModifyCommentPort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentEditService implements EditCommentUseCase {

    private final ModifyCommentPort modifyCommentPort;

    @Override
    public void edit(String memberId, Long commentId, String content) {
        Long result = modifyCommentPort.modifyContent(commentId, content, LocalDateTime.now());
        if (result != 1){
            throw new BusinessException(CommentExceptionType.FAIL_MODIFY_COMMENT);
        }
    }

}
