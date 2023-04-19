package refrigerator.back.notification.application.port.in;

public interface ModifyNotificationUseCase {

    // 알림 수정 (안읽음 -> 읽음)
    void modifyNotification(Long id);
}
