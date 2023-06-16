package refrigerator.back.notification.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.adapter.out.dto.OutCommentDetailsDTO;
import refrigerator.back.notification.application.domain.CommentNotificationDetails;
import refrigerator.back.notification.application.domain.Notification;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationResponseDTO toNotificationResponseDTO(Notification notification);

    CommentNotificationDetails toCommentNotificationDetail(OutCommentDetailsDTO dto);

}
