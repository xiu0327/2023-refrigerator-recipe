package refrigerator.integration.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEmailParameterRequestDTO {
    @Pattern(regexp = InputDataFormatCheck.EMAIL_REGEX)
    private String email;
}
