package refrigerator.back.member.application.port.in;

public interface UpdatePasswordUseCase {
    String updatePassword(String email, String newPassword);
}
