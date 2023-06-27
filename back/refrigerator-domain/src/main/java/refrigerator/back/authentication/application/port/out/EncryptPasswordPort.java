package refrigerator.back.authentication.application.port.out;

/**
 * Security 에서 제공하는 암호화 인코더를 통해 비밀번호 암호화
 */
public interface EncryptPasswordPort {
    String encrypt(String password);
}
