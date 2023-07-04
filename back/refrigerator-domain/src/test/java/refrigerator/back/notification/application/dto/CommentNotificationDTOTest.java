package refrigerator.back.notification.application.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentNotificationDTOTest {

    @Test
    @DisplayName("댓글 좋아요 알림 DTO 테스트")
    void commentNotificationDTOTest(){
        CommentNotificationDTO dto = CommentNotificationDTO.builder()
                .authorId("1L")
                .recipeId(1L)
                .build();

        assertThat(dto.getAuthorId()).isEqualTo("1L");
        assertThat(dto.getRecipeId()).isEqualTo(1L);
    }

}