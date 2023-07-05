package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.back.comment.application.port.out.CreateCommentPort;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService implements WriteCommentUseCase {

    private final CreateCommentPort createCommentPort;

    @Override
    public Long write(Long recipeId, String memberId, String content, LocalDateTime writeDate) {
        Comment comment = new Comment(recipeId, memberId, content, writeDate);
        return createCommentPort.create(comment);
    }

}
