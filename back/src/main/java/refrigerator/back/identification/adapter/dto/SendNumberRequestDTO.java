package refrigerator.back.identification.adapter.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendNumberRequestDTO {
    private String email;
}
