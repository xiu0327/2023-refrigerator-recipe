package refrigerator.back.notification.outbound.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OutCommentNotificationDTOTest {

    @Test
    @DisplayName("OutCommentNotificationDTO 테스트")
    void outCommentNotificationDTOTest() {

        OutCommentNotificationDTO dto = OutCommentNotificationDTO.builder()
                .authorId("email123@gmail.com")
                .recipeId(1L)
                .build();

        assertThat(dto.getAuthorId()).isEqualTo("email123@gmail.com");
        assertThat(dto.getRecipeId()).isEqualTo(1L);
    }
}