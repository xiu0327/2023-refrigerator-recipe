package refrigerator.back.authentication.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
