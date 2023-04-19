package refrigerator.back.authentication.application.port.out;

public interface AuthenticatePort {
    String authenticate(String username, String password);
}
