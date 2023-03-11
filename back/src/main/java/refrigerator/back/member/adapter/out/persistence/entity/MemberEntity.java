package refrigerator.back.member.adapter.out.persistence.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class MemberEntity {

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

    @CreatedDate
    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "status", nullable = false, length = 50)
    private String memberStatus;

    @Column(name = "profile")
    private String profile;

    @Column(name = "role")
    private String memberRole;

    /* 나중에 회원 권한 구현 예정 */

    @Builder
    public MemberEntity(String email, String password, String nickname, LocalDateTime joinDate, String memberStatus, String memberRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.joinDate = joinDate;
        this.memberStatus = memberStatus;
        this.memberRole = memberRole;
    }
}
