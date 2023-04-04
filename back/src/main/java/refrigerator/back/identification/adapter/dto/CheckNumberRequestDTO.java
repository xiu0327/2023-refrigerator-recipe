package refrigerator.back.identification.adapter.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckNumberRequestDTO {
    private String email;
    private String inputCode;
}
