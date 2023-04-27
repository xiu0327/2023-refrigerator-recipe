package refrigerator.back.notification.adapter.in.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationResponseDTO {

    private Long id;
    private String image;
    private String message;
    private String registerTime;
    private String path;
    private boolean readStatus;
}
