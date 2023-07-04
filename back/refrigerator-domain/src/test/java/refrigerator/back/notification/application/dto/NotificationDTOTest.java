package refrigerator.back.notification.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.notification.application.domain.NotificationType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotificationDTOTest {
    @Test
    @DisplayName("알림 DTO 테스트")
    void notificationDTOTest(){

        NotificationDTO dto = NotificationDTO.builder()
                .id(1L)
                .message("test message")
                .type(NotificationType.HEART)
                .registerTime("1 분 전")
                .path("/")
                .readStatus(false)
                .build();

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMessage()).isEqualTo("test message");
        assertThat(dto.getType()).isEqualTo(NotificationType.HEART);
        assertThat(dto.getRegisterTime()).isEqualTo("1 분 전");
        assertThat(dto.getPath()).isEqualTo("/");
        assertThat(dto.isReadStatus()).isEqualTo(false);
    }
}