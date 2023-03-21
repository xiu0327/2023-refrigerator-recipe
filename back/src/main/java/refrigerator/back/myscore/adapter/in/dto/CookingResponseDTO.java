package refrigerator.back.myscore.adapter.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CookingResponseDTO {
    private Long scoreID;
    private Boolean isCreated;
}
