package refrigerator.back.identification.application.port.in;

public interface CheckNumberUseCase {
    Boolean checkAuthenticationNumber(String inputCode, String email);
}
