package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.comment.application.port.in.FindNumberOfCommentsUseCase;
import refrigerator.back.comment.application.port.out.FindNumberOfCommentsPort;

@Service
@RequiredArgsConstructor
public class CommentsCountService implements FindNumberOfCommentsUseCase {

    private final FindNumberOfCommentsPort findNumberOfCommentsPort;

    @Override
    public Integer findNumber(Long recipeId) {
        return findNumberOfCommentsPort.getNumber(recipeId).intValue();
    }
}
