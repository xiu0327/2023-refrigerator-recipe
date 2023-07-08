package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.application.dto.CommentDto;
import refrigerator.back.comment.application.dto.InCommentDto;
import refrigerator.back.comment.application.mapper.CommentMapper;
import refrigerator.back.comment.application.port.in.FindCommentsUseCase;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.FindCommentPort;
import refrigerator.back.global.common.TimeService;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.global.time.ServiceCurrentTime;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentLookUpService implements FindCommentsUseCase {

    private final FindCommentPort findCommentPort;
    private final FindCommentHeartPeoplePort commentHeartPeoplePort;
    private final CommentMapper mapper;
    private final CommentTimeService commentTimeService = new TimeService();
    private final CurrentTime currentTime;

    @Override
    public List<InCommentDto> findComments(Long recipeId, String memberId, CommentSortCondition sortCondition, int page, int size) {
        return mapping(memberId, () -> findCommentPort.findComments(recipeId, memberId, sortCondition, page, size));
    }

    @Override
    public List<InCommentDto> findCommentsPreview(Long recipeId, String memberId, int size) {
        return mapping(memberId, () -> findCommentPort.findPreviewComments(recipeId, memberId, size));
    }

    @Override
    public List<InCommentDto> findMyComments(String memberId, Long recipeId) {
        return mapping(memberId, () -> findCommentPort.findMyComments(memberId, recipeId));
    }

    private List<InCommentDto> mapping(String memberId,
                                       FindCommentQueryCall findCommentQueryCall){
        List<CommentDto> comments = findCommentQueryCall.call();
        Map<Long, Object> peoples = commentHeartPeoplePort.findPeopleMap(memberId);
        return comments.stream().map(comment -> comment.mapping(
                        mapper,
                        commentTimeService,
                        peoples.getOrDefault(comment.getCommentId(), null),
                        currentTime.now()))
                .collect(Collectors.toList());
    }

    interface FindCommentQueryCall{
        List<CommentDto> call();
    }
}
