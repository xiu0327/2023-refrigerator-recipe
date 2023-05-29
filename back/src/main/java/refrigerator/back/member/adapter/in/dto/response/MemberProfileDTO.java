package refrigerator.back.member.adapter.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileDTO {
    private String imageName;
    private String imageUrl;

}
