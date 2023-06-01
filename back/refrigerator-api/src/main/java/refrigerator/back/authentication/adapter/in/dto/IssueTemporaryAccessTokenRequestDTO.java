package refrigerator.back.authentication.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueTemporaryAccessTokenRequestDTO {
    @NotEmpty
    private String email;
}
