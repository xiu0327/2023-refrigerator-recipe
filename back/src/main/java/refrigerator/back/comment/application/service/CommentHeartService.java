package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.comment.application.port.in.AddCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.ReduceCommentHeartUseCase;
import refrigerator.back.comment.application.port.out.CommentHeartUpdatePort;

@Service
@RequiredArgsConstructor
public class CommentHeartService implements AddCommentHeartUseCase, ReduceCommentHeartUseCase {

    private final CommentHeartUpdatePort commentHeartUpdatePort;

    @Override
    public void addHeart(Long commentId) {
        commentHeartUpdatePort.add(commentId);
    }

    @Override
    public void reduceHeart(Long commentId) {
        commentHeartUpdatePort.reduce(commentId);
    }
}
