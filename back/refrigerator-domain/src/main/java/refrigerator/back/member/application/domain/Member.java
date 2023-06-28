package refrigerator.back.member.application.domain;


import lombok.*;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Member {

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

    @Column(name = "create_date", nullable = false)
    private LocalDateTime joinDateTime;

    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberStatus = MemberStatus.STEADY_STATUS;
        this.joinDateTime = LocalDateTime.now();
        this.profile = MemberProfileImage.pickUp();
    }

    /* 비즈니스 로직 */

    public void changePassword(String newPassword){
        if (isEqualPassword(newPassword)){
            throw new BusinessException(MemberExceptionType.EQUAL_OLD_PASSWORD);
        }
        this.password = newPassword;
    }

    public void withdraw(){
        memberStatus = MemberStatus.LEAVE_STATUS;
    }


    public static Member join(String email, String password, String nickname){
        return new Member(email, password, nickname);
    }

    public boolean isEqualPassword(String target){
        return password.equals(target);
    }

    public boolean isWithdrawMember(){
        return memberStatus.equals(MemberStatus.LEAVE_STATUS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email) && Objects.equals(password, member.password) && Objects.equals(nickname, member.nickname) && memberStatus == member.memberStatus && profile == member.profile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nickname, memberStatus, profile);
    }

}
