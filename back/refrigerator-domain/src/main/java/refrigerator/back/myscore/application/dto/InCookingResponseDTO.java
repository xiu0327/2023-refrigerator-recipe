package refrigerator.back.myscore.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InCookingResponseDTO {
    private Long scoreID;
    private Boolean isCreated;
}
