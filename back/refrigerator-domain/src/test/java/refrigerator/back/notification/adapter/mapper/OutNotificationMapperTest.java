package refrigerator.back.notification.adapter.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.notification.adapter.dto.OutCommentNotificationDTO;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;

import static org.assertj.core.api.Assertions.assertThat;

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