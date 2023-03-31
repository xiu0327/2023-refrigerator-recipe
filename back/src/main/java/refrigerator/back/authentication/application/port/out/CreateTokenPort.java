package refrigerator.back.authentication.application.port.out;

public interface CreateTokenPort {
    String createAccessToken(String username, String authority);
    String createRefreshToken(String username, String authority);
}
