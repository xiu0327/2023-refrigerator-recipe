package refrigerator.back.notification.application.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentNotificationDTOTest {

    @Test
    @DisplayName("댓글 알림 DTO 테스트")
    void commentNotificationDTOTest() {

        CommentNotificationDTO dto = CommentNotificationDTO.builder()
                .recipeId(1L)
                .authorId("email123@gmail.com")
                .build();

        assertThat(dto.getRecipeId()).isEqualTo(1L);
        assertThat(dto.getAuthorId()).isEqualTo("email123@gmail.com");
    }
}