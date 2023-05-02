package refrigerator.back.authentication.application.port.out;

public interface ValidateTokenPort {
    boolean validate(String accessToken, String refreshToken);
    boolean isExpired(String token);
}
