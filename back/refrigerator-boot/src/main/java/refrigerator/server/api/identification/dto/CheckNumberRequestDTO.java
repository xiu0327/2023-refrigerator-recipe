package refrigerator.server.api.identification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static refrigerator.server.api.global.common.InputDataFormatCheck.EMAIL_REGEX;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckNumberRequestDTO {

    @Pattern(regexp = EMAIL_REGEX)
    @NotBlank
    private String email;

    @NotBlank
    private String inputCode;
}
