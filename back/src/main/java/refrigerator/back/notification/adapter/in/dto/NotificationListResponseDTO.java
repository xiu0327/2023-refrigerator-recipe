package refrigerator.back.notification.adapter.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NotificationListResponseDTO<T> {

    List<T> data;

    public NotificationListResponseDTO(List<T> data) {
        this.data = data;
    }
}
