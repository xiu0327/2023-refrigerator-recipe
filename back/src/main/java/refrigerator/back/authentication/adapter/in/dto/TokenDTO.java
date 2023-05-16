package refrigerator.back.authentication.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public void removeRefreshToken(){
        this.refreshToken = null;
    }
}
