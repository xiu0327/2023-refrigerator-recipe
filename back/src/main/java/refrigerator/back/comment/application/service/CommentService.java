package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.in.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.in.EditCommentUseCase;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.back.comment.application.port.out.CommentFindOnePort;
import refrigerator.back.comment.application.port.out.CommentHeartCreatePort;
import refrigerator.back.comment.application.port.out.CommentWritePort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService implements WriteCommentUseCase, DeleteCommentUseCase, EditCommentUseCase {

    private final CommentFindOnePort commentFindOnePort;
    private final CommentWritePort commentWritePort;
    private final CommentHeartCreatePort commentHeartCreatePort;

    @Override
    public Long write(Long recipeId, String memberId, String content) {
        Long commentId = commentWritePort.persist(Comment.write(recipeId, memberId, content));
        commentHeartCreatePort.create(new CommentHeart(commentId, 0));
        return commentId;
    }

    @Override
    public Long delete(Long commentId) {
        Comment comment = commentFindOnePort.findCommentById(commentId)
                .orElseThrow(() -> new BusinessException(CommentExceptionType.NOT_FOUND_COMMENT));
        comment.delete();
        return comment.getCommentID();
    }

    @Override
    public Long edit(Long commentId, String content) {
        Comment comment = commentFindOnePort.findCommentById(commentId)
                .orElseThrow(() -> new BusinessException(CommentExceptionType.NOT_FOUND_COMMENT));
        comment.edit(content);
        return comment.getCommentID();
    }
}
