package refrigerator.server.api.identification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static refrigerator.server.api.global.common.InputDataFormatCheck.*;

@Getter
@NoArgsConstructor
public class SendNumberRequestDTO {

    @Pattern(regexp = EMAIL_REGEX)
    @NotBlank
    private String email;
}
