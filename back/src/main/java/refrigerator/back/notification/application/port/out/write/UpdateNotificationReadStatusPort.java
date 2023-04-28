package refrigerator.back.notification.application.port.out.write;

public interface UpdateNotificationReadStatusPort {
    void updateReadStatus(Long notificationId, boolean status);
}
