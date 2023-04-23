package refrigerator.back.notification.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDTO {
    // @Validated
    private Long id;
    private String image;
    private String message;
    private String registerTime;
    private String path;
    private boolean readStatus;
}
