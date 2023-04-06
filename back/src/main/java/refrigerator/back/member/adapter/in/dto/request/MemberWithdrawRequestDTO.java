package refrigerator.back.member.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberWithdrawRequestDTO extends InputDataFormatCheck {

    private String password;

    @Override
    public void check() {
        inputCheck(PASSWORD_REGEX, password, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
    }
}
