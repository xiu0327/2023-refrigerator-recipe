package refrigerator.back.member.application.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.BaseTimeEntity;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile", nullable = false, length = 100)
    private MemberProfileImage profile;

    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberStatus = MemberStatus.STEADY_STATUS;
    }

    /* 나중에 회원 권한 구현 예정 */
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

    public static Member join(String email, String password, String nickname){
        Member member = new Member(email, password, nickname);
        int random = new Random().nextInt(MemberProfileImage.values().length);
        member.initializeProfile(random);
        return member;
    }
}
