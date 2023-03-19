package refrigerator.back.member.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Data
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
