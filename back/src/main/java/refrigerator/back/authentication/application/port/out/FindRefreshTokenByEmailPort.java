package refrigerator.back.authentication.application.port.out;

public interface FindRefreshTokenByEmailPort {
    String findRefreshToken(String email);
}
