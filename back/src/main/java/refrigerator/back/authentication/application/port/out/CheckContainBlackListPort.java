package refrigerator.back.authentication.application.port.out;

public interface CheckContainBlackListPort {
    Boolean checkContainBlackList(String token);
}
