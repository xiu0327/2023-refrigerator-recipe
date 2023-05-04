package refrigerator.back.member.adapter.in.dto.request;

import lombok.*;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberNicknameUpdateRequestDTO {
    @Pattern(regexp = InputDataFormatCheck.NICKNAME_REGEX)
    private String nickname;
}
