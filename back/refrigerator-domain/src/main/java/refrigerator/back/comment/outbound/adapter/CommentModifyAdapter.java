package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.outbound.repository.query.CommentUpdateQueryRepository;
import refrigerator.back.comment.application.port.out.ModifyCommentPort;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class CommentModifyAdapter implements ModifyCommentPort {

    private final CommentUpdateQueryRepository updateQueryRepository;

    @Override
    public Long modifyContent(Long id, String content, LocalDateTime now) {
        return updateQueryRepository.updateCommentToContent(id, content, now);
    }

    @Override
    public void modifyHeartCount(Long id, int value) {
        updateQueryRepository.updateCommentToCount(id, value);
    }
}
