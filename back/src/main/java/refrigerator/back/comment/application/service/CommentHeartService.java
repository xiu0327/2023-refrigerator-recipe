package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.out.repository.CommentRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.port.in.heart.AddCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.heart.ReduceCommentHeartUseCase;
import refrigerator.back.comment.application.port.in.people.FindLikedPeopleListUseCase;
import refrigerator.back.comment.application.port.out.CommentFindOnePort;
import refrigerator.back.comment.application.port.out.CommentHeartPeopleReadPort;
import refrigerator.back.comment.application.port.out.CommentHeartPeopleWritePort;
import refrigerator.back.comment.application.port.out.CommentHeartUpdatePort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;
import refrigerator.back.notification.application.port.out.WriteNotificationPort;
import refrigerator.back.notification.application.service.NotificationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentHeartService implements AddCommentHeartUseCase, ReduceCommentHeartUseCase, FindLikedPeopleListUseCase {

    private final CommentHeartUpdatePort commentHeartUpdatePort;
    private final CommentHeartPeopleReadPort commentHeartPeopleReadPort;
    private final CommentHeartPeopleWritePort commentHeartPeopleWritePort;
    // 도현
    private final CommentFindOnePort commentFindOnePort;
    private final NotificationService notificationService;


    @Override
    @Transactional
    public Long addHeart(String memberId, Long commentId) {
        Long peopleId = commentHeartPeopleWritePort.addHeartPeople(new CommentHeartPeople(memberId, commentId));
        commentHeartUpdatePort.add(commentId);

        Comment comment = commentFindOnePort.findCommentById(commentId)
                .orElseThrow(() -> new BusinessException(CommentExceptionType.NOT_FOUND_COMMENT));

        notificationService.createCommentHeartNotification(memberId, comment);

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
