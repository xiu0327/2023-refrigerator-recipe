package refrigerator.back.member.adapter.in.dto.request;

import lombok.*;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdatePasswordRequestDTO {
    @Pattern(regexp = InputDataFormatCheck.PASSWORD_REGEX)
    private String password;

}
