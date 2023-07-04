package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.domain.CommentHeartValue;
import refrigerator.back.comment.application.port.in.heart.AddCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.heart.ReduceCommentHeartUseCase;
import refrigerator.back.comment.application.port.out.ChangeCommentHeartCountPort;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.RemoveCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.SaveCommentHeartPeoplePort;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentHeartChangeService implements AddCommentHeartUseCase, ReduceCommentHeartUseCase {

    private final ChangeCommentHeartCountPort changeCommentHeartCountPort;
    private final FindCommentHeartPeoplePort findCommentHeartPeoplePort;
    private final SaveCommentHeartPeoplePort saveCommentHeartPeoplePort;
    private final RemoveCommentHeartPeoplePort removeCommentHeartPeoplePort;

    @Override
    public void addHeart(Long commentId, String memberId) {
        Optional<CommentHeartPeople> findPeople = findCommentHeartPeoplePort.findPeopleOne(commentId, memberId);
        if (findPeople.isPresent()){
            log.info("사용자가 중복된 하트 수 증가 요청을 보냈습니다.");
            return ;
        }
        changeCommentHeartCountPort.change(commentId, CommentHeartValue.ADD);
        CommentHeartPeople people = new CommentHeartPeople(commentId, memberId);
        saveCommentHeartPeoplePort.save(people);
    }

    @Override
    public void reduceHeart(Long commentId, String peopleId) {
        changeCommentHeartCountPort.change(commentId, CommentHeartValue.REDUCE);
        removeCommentHeartPeoplePort.remove(peopleId);
    }
}
