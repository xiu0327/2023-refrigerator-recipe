package refrigerator.back.member.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.member.exception.MemberExceptionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequestDTO extends InputDataFormatCheck {
    private String email;
    private String password;
    private String nickname;

    @Override
    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        inputCheck(PASSWORD_REGEX, password, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
        inputCheck(NICKNAME_REGEX, nickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
    }
}
