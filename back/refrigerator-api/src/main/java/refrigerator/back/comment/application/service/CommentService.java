package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.in.comment.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.in.comment.EditCommentUseCase;
import refrigerator.back.comment.application.port.in.comment.WriteCommentUseCase;
import refrigerator.back.comment.application.port.out.CommentWritePort;
import refrigerator.back.comment.application.port.out.CreateCommentHeartPort;
import refrigerator.back.comment.application.port.out.DeleteCommentHeartPort;
import refrigerator.back.comment.application.port.out.FindOneCommentPort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.domain.BusinessException;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService implements WriteCommentUseCase, DeleteCommentUseCase, EditCommentUseCase {

    private final FindOneCommentPort commentFindOnePort;
    private final CommentWritePort commentWritePort;
    private final CreateCommentHeartPort commentHeartCreatePort;
    private final DeleteCommentHeartPort commentHeartDeletePort;

    @Override
    public Long write(Long recipeId, String memberId, String content) {
        Long commentId = commentWritePort.persist(Comment.write(recipeId, memberId, content));
        commentHeartCreatePort.create(new CommentHeart(commentId));
        return commentId;
    }

    @Override
    public Long delete(String memberId, Long commentId) {
        Comment comment = commentFindOnePort.findCommentById(commentId)
                .orElseThrow(() -> new BusinessException(CommentExceptionType.NOT_FOUND_COMMENT));
        comment.isEqualsAuthor(memberId);
        comment.delete();
        commentHeartDeletePort.delete(commentId);
        return comment.getCommentID();
    }

    @Override
    public Long edit(String memberId, Long commentId, String content) {
        Comment comment = commentFindOnePort.findCommentById(commentId)
                .orElseThrow(() -> new BusinessException(CommentExceptionType.NOT_FOUND_COMMENT));
        comment.isEqualsAuthor(memberId);
        comment.edit(content);
        return comment.getCommentID();
    }
}
