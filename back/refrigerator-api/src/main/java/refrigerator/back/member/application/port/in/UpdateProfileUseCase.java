package refrigerator.back.member.application.port.in;

public interface UpdateProfileUseCase {
    void updateProfile(String email, String newProfileName);
}
