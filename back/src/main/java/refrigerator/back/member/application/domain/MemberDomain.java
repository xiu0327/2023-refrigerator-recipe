package refrigerator.back.member.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDomain {

    private Long memberID;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime joinDate;
    private MemberStatus memberStatus;
    private MemberProfileImage profile;

    private MemberDomain(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberStatus = MemberStatus.STEADY_STATUS;
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

    public void withdraw(String password){
        isEqualPassword(password);
        this.memberStatus = MemberStatus.LEAVE_STATUS;
    }

    public void changeMemberStatus(MemberStatus status){
        this.memberStatus = status;
    }

    private void initializeProfile(int random){
        this.profile = MemberProfileImage.pickUp(random);
    }

    public void isEqualPassword(String password){
        if (!this.password.equals(password)){
            throw new BusinessException(MemberExceptionType.NOT_EQUAL_PASSWORD);
        }
    }


    public static MemberDomain join(String email, String password, String nickname){
        MemberDomain member = new MemberDomain(email, password, nickname);
        int random = new Random().nextInt(MemberProfileImage.values().length);
        member.initializeProfile(random);
        return member;
    }
}
