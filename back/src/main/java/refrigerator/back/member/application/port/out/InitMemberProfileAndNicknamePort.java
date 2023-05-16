package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.MemberProfileImage;

public interface InitMemberProfileAndNicknamePort {
    void initNicknameAndProfile(String email, String nickname, MemberProfileImage profile);
}
