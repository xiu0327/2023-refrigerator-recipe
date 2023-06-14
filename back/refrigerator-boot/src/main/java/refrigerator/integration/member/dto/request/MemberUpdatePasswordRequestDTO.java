package refrigerator.integration.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdatePasswordRequestDTO {
    @Pattern(regexp = InputDataFormatCheck.PASSWORD_REGEX)
    private String password;

}
