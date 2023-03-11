package refrigerator.back.member.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MemberRole {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN")
    ;

    private String role;

    public static MemberRole lookup(String role){
        return Arrays.stream(MemberRole.values())
                .filter(r -> r.getRole().equals(role))
                .findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER_ROLE));
    }
}
