package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.notification.application.domain.CommentNotificationDetails;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;
import refrigerator.back.notification.application.port.out.read.FindCommentDetailsPort;
import refrigerator.back.notification.application.port.out.read.FindSenderNicknamePort;
import refrigerator.back.notification.application.port.out.write.ModifyMemberNotificationPort;
import refrigerator.back.notification.application.port.out.write.SaveNotificationPort;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateNotificationService implements CreateCommentHeartNotificationUseCase {

    private final FindSenderNicknamePort findSenderNicknamePort;
    private final FindCommentDetailsPort commentDetailsPort;
    private final SaveNotificationPort saveNotificationPort;
    private final ModifyMemberNotificationPort modifyMemberNotificationPort;

    @Override
//    @Async
    public Long createCommentHeartNotification(String senderId, Long commentId) {
        String senderNickname = findSenderNicknamePort.getNickname(senderId);
        CommentNotificationDetails details = commentDetailsPort.getDetails(commentId);
        Notification notification = Notification.create(
                NotificationType.HEART,
                "/api/comments/heart?recipeId=" + details.getRecipeId() + "&page=0",
                details.getAuthorId(),
                HttpMethod.GET.name()
        );
        notification.setMessage(senderNickname);
        modifyMemberNotificationPort.modify(details.getAuthorId(), true);
        return saveNotificationPort.saveNotification(notification);
    }
}