package refrigerator.back.member.adapter.in.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.api.InputDataFormatCheck;

import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberNicknameUpdateRequestDTO {
    @Pattern(regexp = InputDataFormatCheck.NICKNAME_REGEX)
    private String nickname;
}
