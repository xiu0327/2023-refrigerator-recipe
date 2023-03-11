package refrigerator.back.member.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDuplicateCheckEmailRequestDTO extends InputDataFormatCheck {
    private String email;

    @Override
    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
    }
}
