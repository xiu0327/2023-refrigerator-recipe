package refrigerator.back.notification.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotificationSignDTOTest {

    @Test
    @DisplayName("알림 여부 DTO 테스트")
    void notificationSignDTOTest() {
        NotificationSignDTO dto = NotificationSignDTO.builder()
                .sign(false)
                .build();

        assertThat(dto.getSign()).isEqualTo(false);
    }


}