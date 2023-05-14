package refrigerator.back.authentication.application.port.in;

public interface LogoutUseCase {
    void logout(String accessToken);
}
