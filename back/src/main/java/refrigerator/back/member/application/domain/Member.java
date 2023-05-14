package refrigerator.back.member.application.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.common.BaseTimeEntity;
import refrigerator.back.global.exception.BusinessException;

import javax.persistence.*;

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

    /* 비즈니스 로직 */

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    public void withdraw(){
        memberStatus = MemberStatus.LEAVE_STATUS;
    }

    private void initializeProfile(){
        this.profile = MemberProfileImage.PROFILE_NOT_SELECT;
    }

    public static Member join(String email, String password, String nickname){
        Member member = new Member(email, password, nickname);
        member.initializeProfile();
        return member;
    }
}
