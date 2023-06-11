package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.port.in.heart.AddCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.heart.ReduceCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.people.FindLikedPeopleListUseCase;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.UpdateCommentHeartPort;
import refrigerator.back.comment.application.port.out.WriteCommentHeartPeoplePort;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentHeartService implements AddCommentHeartUseCase, ReduceCommentHeartUseCase, FindLikedPeopleListUseCase {

    private final UpdateCommentHeartPort commentHeartUpdatePort;
    private final FindCommentHeartPeoplePort commentHeartPeopleReadPort;
    private final WriteCommentHeartPeoplePort commentHeartPeopleWritePort;


    @Override
    @Transactional
    public Long addHeart(String memberId, Long commentId) {
        Long peopleId = commentHeartPeopleWritePort.addHeartPeople(new CommentHeartPeople(memberId, commentId));
        commentHeartUpdatePort.add(commentId);
        return peopleId;
    }

    @Override
    @Transactional
    public void reduceHeart(String memberId, Long commentId) {
        commentHeartPeopleWritePort.removeHeartPeople(memberId, commentId);
        commentHeartUpdatePort.reduce(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findLikedPeople(String memberId) {
        return commentHeartPeopleReadPort.findLikedComment(memberId);
    }
}
