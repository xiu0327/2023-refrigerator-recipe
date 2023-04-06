package refrigerator.back.member.adapter.in.dto.request;

import lombok.*;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberFindPasswordRequestDTO extends InputDataFormatCheck {

    private String email;

    @Override
    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
    }
}
