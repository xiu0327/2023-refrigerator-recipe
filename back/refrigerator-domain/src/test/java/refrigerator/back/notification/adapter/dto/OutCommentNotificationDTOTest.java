package refrigerator.back.notification.adapter.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OutCommentNotificationDTOTest {

    @Test
    @DisplayName("댓글 좋아요 알림 Out DTO 테스트")
    void outCommentNotificationDTOTest(){
        OutCommentNotificationDTO dto = OutCommentNotificationDTO.builder()
                .authorId("1L")
                .recipeId(1L)
                .build();

        assertThat(dto.getAuthorId()).isEqualTo("1L");
        assertThat(dto.getRecipeId()).isEqualTo(1L);
    }
}