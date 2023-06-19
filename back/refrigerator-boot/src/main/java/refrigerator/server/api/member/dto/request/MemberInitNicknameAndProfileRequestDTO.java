package refrigerator.server.api.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInitNicknameAndProfileRequestDTO {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String imageName;

}
