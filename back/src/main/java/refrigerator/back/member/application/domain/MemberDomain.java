package refrigerator.back.member.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MemberDomain {

    private Long memberID;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime joinDate;
    private MemberStatus memberStatus;
    private MemberProfileImage profile;
    private MemberRole memberRole;

    private MemberDomain(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberStatus = MemberStatus.STEADY_STATUS;
        this.memberRole = MemberRole.ROLE_USER;
    }

    /* 비즈니스 로직 */

    public void updateNickname(String newNickname){
        this.nickname = newNickname;
    }

    public void updateProfile(String newProfileName){
        this.profile = MemberProfileImage.findImageByName(newProfileName);
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    public void changeMemberStatus(MemberStatus status){
        this.memberStatus = status;
    }

    public void pickUpProfile(int random){
        this.profile = MemberProfileImage.pickUp(random);
    }


    public static MemberDomain join(String email, String password, String nickname){
        return new MemberDomain(email, password, nickname);
    }
}
