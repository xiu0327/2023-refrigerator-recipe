package refrigerator.back.member.adapter.dto;

import lombok.*;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberNicknameUpdateRequestDTO extends InputDataFormatCheck {

    private String newNickname;

    @Override
    public void check() {
        inputCheck(NICKNAME_REGEX, newNickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
    }
}
