package refrigerator.back.authentication.application.port.out;

public interface FindEmailByToken {
    String findEmailByToken(String token);
}
