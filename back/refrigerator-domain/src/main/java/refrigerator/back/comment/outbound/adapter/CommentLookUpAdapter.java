package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.port.out.FindNumberOfCommentsPort;
import refrigerator.back.comment.outbound.dto.OutCommentDtoCollection;
import refrigerator.back.comment.outbound.mapper.OutCommentMapper;
import refrigerator.back.comment.outbound.repository.query.CommentSelectQueryRepository;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.port.out.FindCommentPort;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentLookUpAdapter implements FindCommentPort {

    private final CommentSelectQueryRepository queryRepository;
    private final OutCommentMapper mapper;

    @Override
    public List<CommentDto> findComments(Long recipeId,
                                         String memberId,
                                         CommentSortCondition sortCondition,
                                         int page, int size) {
        OutCommentDtoCollection collection = queryRepository.selectComments(recipeId, memberId, PageRequest.of(page, size), sortCondition);
        return collection.mapping(mapper);

    }

    @Override
    public List<CommentDto> findPreviewComments(Long recipeId, String memberId, int size) {
        OutCommentDtoCollection collection = queryRepository.selectPreviewComments(recipeId, PageRequest.of(0, 3));
        return collection.mapping(mapper);
    }

    @Override
    public List<CommentDto> findMyComments(String memberId, Long recipeId) {
        OutCommentDtoCollection collection = queryRepository.selectMyComments(memberId, recipeId);
        return collection.mapping(mapper);
    }

}
