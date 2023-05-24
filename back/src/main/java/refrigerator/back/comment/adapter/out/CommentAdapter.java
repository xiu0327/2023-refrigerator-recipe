package refrigerator.back.comment.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.adapter.out.repository.CommentRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.port.out.CommentWritePort;

@Repository
@RequiredArgsConstructor
public class CommentAdapter implements CommentWritePort {

    private final CommentRepository repository;

    @Override
    public Long persist(Comment comment) {
        repository.save(comment);
        return comment.getCommentID();
    }

}
