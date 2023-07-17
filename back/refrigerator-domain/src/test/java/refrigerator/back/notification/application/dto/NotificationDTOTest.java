package refrigerator.back.notification.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.notification.application.domain.NotificationType;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationDTOTest {

    @Test
    @DisplayName("알림 DTO 테스트")
    void notificationDTOTest() {
        NotificationDTO dto = NotificationDTO.builder()
                .id(1l)
                .path("/")
                .message("test message")
                .readStatus(false)
                .type(NotificationType.HEART)
                .registerTime("2 일 전")
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getRegisterTime()).isEqualTo("2 일 전");
        assertThat(dto.getPath()).isEqualTo("/");
        assertThat(dto.getMessage()).isEqualTo("test message");
        assertThat(dto.getType()).isEqualTo(NotificationType.HEART);
        assertThat(dto.isReadStatus()).isEqualTo(false);
        
    }
}