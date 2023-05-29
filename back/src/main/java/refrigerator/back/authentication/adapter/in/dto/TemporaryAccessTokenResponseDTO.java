package refrigerator.back.authentication.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryAccessTokenResponseDTO {
    private String grantType;
    private String authToken;
}
