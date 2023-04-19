package refrigerator.back.authentication.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenRequestDTO {
    private String accessToken;
    private String refreshToken;
}
