package refrigerator.back.member.adapter.in.dto.request;

import lombok.*;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequestDTO extends InputDataFormatCheck {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;

    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        inputCheck(PASSWORD_REGEX, password, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
        inputCheck(NICKNAME_REGEX, nickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
    }
}