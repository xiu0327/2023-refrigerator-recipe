package refrigerator.back.authentication.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ParseClaimsDto {

    private String email;
    private String role;
    private Date expiration;
}
