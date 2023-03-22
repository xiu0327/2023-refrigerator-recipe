package refrigerator.back.myscore.adapter.in.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InCookingResponseDTO {
    private Long scoreID;
    private Boolean isCreated;
}
