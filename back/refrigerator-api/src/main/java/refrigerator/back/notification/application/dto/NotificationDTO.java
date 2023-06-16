package refrigerator.back.notification.application.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationDTO {

    private Long id;
    private String message;
    private String type;
    private String registerTime;
    private String path;
    private boolean readStatus;
}
