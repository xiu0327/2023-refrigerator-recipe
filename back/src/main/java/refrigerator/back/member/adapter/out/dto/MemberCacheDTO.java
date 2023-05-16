package refrigerator.back.member.adapter.out.dto;

import lombok.*;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCacheDTO implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private MemberStatus memberStatus;
    private MemberProfileImage profile;
    private LocalDateTime createDate;
}
