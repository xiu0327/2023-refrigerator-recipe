package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;

public interface UpdateMemberPort {
    void update(Member member);
    void updateNickname(String email, String nickname);
    void updateProfile(String email, MemberProfileImage profile);
}
