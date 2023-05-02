package refrigerator.back.authentication.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenInfoDTO {
    private String email;
    private String role;
}
