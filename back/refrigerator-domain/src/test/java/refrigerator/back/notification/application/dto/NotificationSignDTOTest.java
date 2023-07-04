package refrigerator.back.notification.application.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificationSignDTOTest {

    @Test
    @DisplayName("알림 아이콘 활성화 DTO 테스트")
    void notificationSignDTOTest() {
        NotificationSignDTO dto = NotificationSignDTO.builder()
                .sign(false)
                .build();

        assertThat(dto.getSign()).isEqualTo(false);
    }
}