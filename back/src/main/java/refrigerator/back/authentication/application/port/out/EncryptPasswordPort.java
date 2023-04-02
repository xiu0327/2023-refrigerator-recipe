package refrigerator.back.authentication.application.port.out;

public interface EncryptPasswordPort {
    String encrypt(String rawPassword);
    Boolean match(String password, String rawPassword);
}
