package refrigerator.back.notification.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.adapter.mapper.OutNotificationMapper;
import refrigerator.back.notification.adapter.repository.NotificationQueryRepository;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.port.out.commentHeart.FindCommentDetailsPort;
import refrigerator.back.notification.application.port.out.commentHeart.FindSenderNicknamePort;
import refrigerator.back.notification.exception.NotificationExceptionType;


@Repository
@RequiredArgsConstructor
public class CommentHeartNotificationAdapter implements FindSenderNicknamePort, FindCommentDetailsPort {

    private final NotificationQueryRepository notificationQueryRepository;
    private final OutNotificationMapper mapper;

    @Override
    public String getNickname(String memberId) {
        return notificationQueryRepository.getNickname(memberId)
                .orElseThrow(() -> new BusinessException(NotificationExceptionType.TEST_ERROR));
    }

    @Override
    public CommentNotificationDTO getDetails(Long commentId) {
        return mapper.toCommentNotificationDetail(
                notificationQueryRepository.getDetails(commentId)
                .orElseThrow(() -> new BusinessException(NotificationExceptionType.TEST_ERROR)));
    }
}
