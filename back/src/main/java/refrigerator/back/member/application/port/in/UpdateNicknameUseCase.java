package refrigerator.back.member.application.port.in;

public interface UpdateNicknameUseCase {
    void updateNickname(String email, String newNickname);
}
