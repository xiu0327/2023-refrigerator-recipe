package refrigerator.back.member.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileUpdateRequestDTO {

    private String newProfileName;

}
