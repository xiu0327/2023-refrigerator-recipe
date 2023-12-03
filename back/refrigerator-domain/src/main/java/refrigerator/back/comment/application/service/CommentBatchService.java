package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.port.batch.DeleteCommentBatchPort;
import refrigerator.back.comment.application.port.batch.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.batch.FindCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.batch.FindDeletedCommentPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentBatchService implements DeleteCommentUseCase {

    private final DeleteCommentBatchPort deleteCommentBatchPort;
    private final FindDeletedCommentPort findDeletedCommentPort;
    private final FindCommentHeartPeoplePort findCommentHeartPeoplePort;

    @Override
    @Transactional
    public void deleteComment() {
        List<Long> ids = findDeletedCommentPort.findDeletedComment();
        for (Long commentId : ids) {
            List<CommentHeartPeople> commentHeartPeopleList = findCommentHeartPeoplePort.findByCommentID(commentId);
            for (CommentHeartPeople commentHeartPeople : commentHeartPeopleList) {
                deleteCommentBatchPort.deleteCommentHeartPeople(commentHeartPeople);
            }
        }
        Long deletedCommentHeart = deleteCommentBatchPort.deleteCommentHeart();
        Long deletedComment = deleteCommentBatchPort.deleteComment();
        if(!deletedComment.equals(deletedCommentHeart)){
            throw new RuntimeException();
        }
    }
}
