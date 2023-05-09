package refrigerator.back.member.adapter.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberWithdrawRequestDTO extends InputDataFormatCheck {
    @NotEmpty
    private String password;
}
