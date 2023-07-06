package refrigerator.back.member.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class OutMemberDto {

    private String nickname;
    private String profileImageName;

    @QueryProjection
    public OutMemberDto(String nickname, String profileImageName) {
        this.nickname = nickname;
        this.profileImageName = profileImageName;
    }
}
