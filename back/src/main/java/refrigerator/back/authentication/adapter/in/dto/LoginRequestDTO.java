package refrigerator.back.authentication.adapter.in.dto;

import lombok.*;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO extends InputDataFormatCheck {
    private String email;
    private String password;

    @Override
    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        inputCheck(PASSWORD_REGEX, password, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
    }
}