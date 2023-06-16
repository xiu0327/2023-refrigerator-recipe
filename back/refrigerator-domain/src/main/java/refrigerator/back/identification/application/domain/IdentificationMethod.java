package refrigerator.back.identification.application.domain;

public interface IdentificationMethod {
    void sendAuthenticationCode(String email, String code);
}
