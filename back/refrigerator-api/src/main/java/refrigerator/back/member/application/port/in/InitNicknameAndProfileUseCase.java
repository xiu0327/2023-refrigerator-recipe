package refrigerator.back.member.application.port.in;

public interface InitNicknameAndProfileUseCase {
    void initNicknameAndProfile(String email, String nickname, String imageFileName);
}
