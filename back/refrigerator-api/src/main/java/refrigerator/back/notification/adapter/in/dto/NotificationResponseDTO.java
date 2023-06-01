package refrigerator.back.notification.adapter.in.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationResponseDTO {

    private Long id;
    private String image;
    private String message;
    private String type;
    private String registerTime;
    private String path;
    private boolean readStatus;
}
