package refrigerator.back.comment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.adapter.mapper.CommentMapper;
import refrigerator.back.comment.adapter.out.dto.OutCommentDTO;
import refrigerator.back.comment.adapter.out.repository.CommentRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentDto;
import refrigerator.back.comment.application.domain.CommentListDto;
import refrigerator.back.comment.application.port.out.FindMyCommentListPort;
import refrigerator.back.comment.application.port.out.FindOneCommentPort;
import refrigerator.back.comment.application.port.out.CommentReadPort;
import refrigerator.back.comment.application.port.out.CommentWritePort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static refrigerator.back.comment.adapter.out.persistence.CommentSortCondition.*;

@Repository
@RequiredArgsConstructor
public class CommentAdapter implements CommentWritePort {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Override
    public Long persist(Comment comment) {
        repository.save(comment);
        return comment.getCommentID();
    }

}
