package refrigerator.back.authentication.application.port.out;

public interface CreateAuthenticationPort {
    String authenticate(String username, String password);
}
