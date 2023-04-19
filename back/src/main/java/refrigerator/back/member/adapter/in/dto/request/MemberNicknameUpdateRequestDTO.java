package refrigerator.back.member.adapter.in.dto.request;

import lombok.*;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberNicknameUpdateRequestDTO extends InputDataFormatCheck {

    private String nickname;

    @Override
    public void check() {
        inputCheck(NICKNAME_REGEX, nickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
    }
}
