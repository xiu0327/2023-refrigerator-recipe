package refrigerator.back.member.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEmailParameterRequestDTO extends InputDataFormatCheck {
    private String email;

    @Override
    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
    }
}
