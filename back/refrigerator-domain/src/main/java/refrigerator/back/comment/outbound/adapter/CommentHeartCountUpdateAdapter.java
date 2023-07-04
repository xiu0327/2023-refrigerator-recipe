package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.domain.CommentHeartValue;
import refrigerator.back.comment.application.port.out.ChangeCommentHeartCountPort;
import refrigerator.back.comment.outbound.repository.query.CommentUpdateQueryRepository;

@Repository
@RequiredArgsConstructor
public class CommentHeartCountUpdateAdapter implements ChangeCommentHeartCountPort {

    private final CommentUpdateQueryRepository commentQueryRepository;

    @Override
    public Long change(Long commentId, CommentHeartValue value) {
        return commentQueryRepository.updateCommentToCount(commentId, value.getValue());
    }
}
