package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.ModifyNotificationUseCase;
import refrigerator.back.notification.application.port.out.ReadNotificationPort;
import refrigerator.back.notification.application.port.out.WriteNotificationPort;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService implements FindNotificationListUseCase, ModifyNotificationUseCase {

    private final WriteNotificationPort writeNotificationPort;
    private final ReadNotificationPort readNotificationPort;

    // 알림 목록 조회
    @Override
    public List<NotificationResponseDTO> getNotificationList(String email, int page, int size) {

        MemberNotification memberNotification = readNotificationPort.getMemberNotification(email);
        memberNotification.signOff();
        writeNotificationPort.saveMemberNotification(memberNotification);

        return readNotificationPort.getNotificationList(email, page, size);
    }

    // 멤버 알림 생성 (회원 가입시)
    public Long createMemberNotification(String email){
        return writeNotificationPort.saveMemberNotification(MemberNotification.create(email));
    }

    // 신규 알림 생성 조회
    @Override
    @Transactional(readOnly = true)
    public boolean getNotificationSign(String email) {
        return readNotificationPort.getMemberNotification(email).isSign();
    }

    // 알림 수정 (안읽음 -> 읽음)
    @Override
    public void modifyNotification(Long id) {

        Notifications notification = readNotificationPort.getNotification(id);
        notification.isRead();
        writeNotificationPort.saveNotification(notification);
    }

    // 댓글 좋아요 알림 생성
    public Long createCommentHeartNotification(String anotherId, Comment comment) {

        String memberId = comment.getMemberID();

        Long id = writeNotificationPort.saveNotification(
                Notifications.create(4,
                        createNotificationMessage(anotherId, memberId),
                        4,
                        "localhost:8080/api/recipe/" + comment.getRecipeID(), // 수정
                        "GET",
                        memberId));

        // Sign 변경
        MemberNotification memberNotification = readNotificationPort.getMemberNotification(memberId);
        memberNotification.signOn();
        writeNotificationPort.saveMemberNotification(memberNotification);

        return id;
    }

    private String createNotificationMessage(String anotherId, String memberId) {
        return anotherId + "님이 " + memberId + "님의 댓글을 좋아합니다.";
    }
}