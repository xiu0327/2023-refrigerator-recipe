package refrigerator.back.notification.application.port.out;

public interface ModifyMemberNotificationPort {
    void modify(String memberId, boolean value);
}
