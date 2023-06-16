package refrigerator.back.member.application.port.out;


import refrigerator.back.member.application.domain.MemberProfileImage;

public interface ModifyMemberProfilePort {
    void modifyProfile(String email, MemberProfileImage profile);
}
