package refrigerator.back.notification.adapter.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.global.common.TimeService;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.notification.adapter.dto.OutCommentNotificationDTO;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationTimeService;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.mapper.NotificationMapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OutNotificationMapperTest {

    OutNotificationMapper outNotificationMapper = Mappers.getMapper(OutNotificationMapper.class);

    @Test
    @DisplayName("OutCommentNotificationDTO에서 CommentNotificationDTO로 변환")
    void toNotificationDTO() {

        OutCommentNotificationDTO outDto = OutCommentNotificationDTO.builder()
                .authorId("1L")
                .recipeId(1L)
                .build();

        CommentNotificationDTO dto = outNotificationMapper.toCommentNotificationDetail(outDto);

        assertThat(dto.getAuthorId()).isEqualTo("1L");
        assertThat(dto.getRecipeId()).isEqualTo(1L);
    }
}