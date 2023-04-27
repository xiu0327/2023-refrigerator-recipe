package refrigerator.back.notification.application.port.in;

public interface ChangeMemberNotificationSignUseCase {
    void SignOff(String memberId);
    void SingOn(String memberId);
}
