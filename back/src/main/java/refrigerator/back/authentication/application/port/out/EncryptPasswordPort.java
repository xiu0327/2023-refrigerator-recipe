package refrigerator.back.authentication.application.port.out;

public interface EncryptPasswordPort {
    String encrypt(String password);
    Boolean match(String newPassword, String oldPassword);
}
