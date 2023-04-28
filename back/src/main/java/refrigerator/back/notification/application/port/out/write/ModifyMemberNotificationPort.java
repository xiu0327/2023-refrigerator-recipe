package refrigerator.back.notification.application.port.out.write;

public interface ModifyMemberNotificationPort {
    void modify(String memberId, boolean value);
}
