package refrigerator.back.identification.application.port.out;

public interface IdentificationMailSendPort {
    void sendAuthenticationCode(String email, String code);
}
