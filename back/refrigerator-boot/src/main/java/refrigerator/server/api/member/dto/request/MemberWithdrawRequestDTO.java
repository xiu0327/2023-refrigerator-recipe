package refrigerator.server.api.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberWithdrawRequestDTO extends InputDataFormatCheck {
    @NotEmpty
    private String password;
}
