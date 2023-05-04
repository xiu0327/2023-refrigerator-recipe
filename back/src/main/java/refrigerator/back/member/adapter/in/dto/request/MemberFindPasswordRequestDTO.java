package refrigerator.back.member.adapter.in.dto.request;

import lombok.*;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberFindPasswordRequestDTO {
    @NotEmpty
    private String email;
}
