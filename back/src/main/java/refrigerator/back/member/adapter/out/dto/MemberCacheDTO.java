package refrigerator.back.member.adapter.out.dto;

import lombok.*;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCacheDTO implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private MemberStatus memberStatus;
    private MemberProfileImage profile;
    private LocalDateTime joinDateTime;

    public MemberCacheDTO(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberCacheDTO cacheDTO = (MemberCacheDTO) o;
        return Objects.equals(id, cacheDTO.id) && Objects.equals(email, cacheDTO.email) && Objects.equals(password, cacheDTO.password) && Objects.equals(nickname, cacheDTO.nickname) && memberStatus == cacheDTO.memberStatus && profile == cacheDTO.profile && Objects.equals(joinDateTime, cacheDTO.joinDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nickname, memberStatus, profile, joinDateTime);
    }
}
