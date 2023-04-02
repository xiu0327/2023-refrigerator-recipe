package refrigerator.back.authentication.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenRequestDTO {
    private String accessToken;
    private String refreshToken;
}
