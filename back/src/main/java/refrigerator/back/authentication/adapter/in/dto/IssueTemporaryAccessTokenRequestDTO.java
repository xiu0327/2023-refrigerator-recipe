package refrigerator.back.authentication.adapter.in.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueTemporaryAccessTokenRequestDTO {
    @NotEmpty
    private String email;
}
