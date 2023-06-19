package refrigerator.back.authentication.application.port.out;

public interface RemoveRefreshTokenPort {
    void removeRefreshToken(String email);
}
