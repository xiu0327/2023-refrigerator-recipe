package refrigerator.back.member.application.port.in;

public interface UpdatePasswordUseCase {
    void updatePassword(String email, String newPassword);
}
