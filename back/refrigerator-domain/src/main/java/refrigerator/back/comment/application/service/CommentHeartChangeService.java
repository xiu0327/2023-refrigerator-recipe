package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.domain.CommentHeartValue;
import refrigerator.back.comment.application.port.in.ChangeCommentHeartCountUseCase;
import refrigerator.back.comment.application.port.out.*;
import refrigerator.back.global.common.RandomUUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentHeartChangeService implements ChangeCommentHeartCountUseCase {

    private final ChangeCommentHeartCountPort changeCommentHeartCountPort;
    private final CheckExistCommentHeartPeoplePort checkExistCommentHeartPeoplePort;
    private final SaveCommentHeartPeoplePort saveCommentHeartPeoplePort;
    private final RemoveCommentHeartPeoplePort removeCommentHeartPeoplePort;
    private final RandomUUID randomUUID;

    @Override
    public void add(Long commentId, String memberId) {
        Boolean isExistPeople = checkExistCommentHeartPeoplePort.checkByCommentIdAndMemberId(commentId, memberId);
        changeCommentHeartCountPort.change(commentId, CommentHeartValue.ADD);
        saveCommentHeartPeoplePort.save(CommentHeartPeople.add(isExistPeople, randomUUID, commentId, memberId));
    }

    @Override
    public void reduce(Long commentId, String peopleId) {
        Boolean isExistPeople = checkExistCommentHeartPeoplePort.checkByPeopleId(peopleId);
        CommentHeartPeople.checkReduceRequest(isExistPeople);
        changeCommentHeartCountPort.change(commentId, CommentHeartValue.REDUCE);
        removeCommentHeartPeoplePort.remove(peopleId);
    }
}
