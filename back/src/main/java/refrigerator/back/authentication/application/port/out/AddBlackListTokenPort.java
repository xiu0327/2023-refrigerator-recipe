package refrigerator.back.authentication.application.port.out;

public interface AddBlackListTokenPort {
    void addToken(String token, long duration);
}
