package refrigerator.back.member.application.port.in;

public interface FindPasswordUseCase {
    void findPassword(String email);
    void updatePassword(String email, String newPassword);
}
