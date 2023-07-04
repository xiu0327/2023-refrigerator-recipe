package refrigerator.back.notification.application.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.global.common.TimeService;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.ingredient.adapter.mapper.OutIngredientMapper;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationTimeService;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.dto.NotificationDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotificationMapperTest {

    NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);

    @Test
    @DisplayName("Notification에서 NotificationDTO로 변환")
    void toNotificationDTO() {

        Notification notification = Notification.builder()
                .id(1L)
                .message("test message")
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .memberId("email123@gmail.com")
                .method(BasicHttpMethod.GET.name())
                .build();

        NotificationTimeService timeService = new TimeService();

        NotificationDTO dto = notificationMapper.toNotificationDTO(notification, timeService.replace(
                LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                LocalDateTime.of(2023, 1, 1, 0, 0, 1)));

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMessage()).isEqualTo("test message");
        assertThat(dto.getType()).isEqualTo(NotificationType.HEART);
        assertThat(dto.getRegisterTime()).isEqualTo("1 초 전");
        assertThat(dto.getPath()).isEqualTo("/");
        assertThat(dto.isReadStatus()).isEqualTo(false);
    } 
}