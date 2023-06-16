package refrigerator.back.notification.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.notification.adapter.out.dto.OutCommentDetailsDTO;
import refrigerator.back.notification.application.domain.CommentNotificationDetails;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    CommentNotificationDetails toCommentNotificationDetail(OutCommentDetailsDTO dto);

}
