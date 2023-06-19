package refrigerator.server.api.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.server.api.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequestDTO extends InputDataFormatCheck {

    @NotEmpty
    @Size(min = 1, max = 200)
    private String email;
    @NotEmpty
    @Size(min = 4, max = 16)
    private String password;
    @NotEmpty
    @Size(min = 3, max = 10)
    private String nickname;

    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        inputCheck(PASSWORD_REGEX, password, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
        inputCheck(NICKNAME_REGEX, nickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
    }
}
