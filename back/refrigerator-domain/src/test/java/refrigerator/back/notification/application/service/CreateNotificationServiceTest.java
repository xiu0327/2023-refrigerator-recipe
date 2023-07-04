package refrigerator.back.notification.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.port.out.commentHeart.FindCommentDetailsPort;
import refrigerator.back.notification.application.port.out.commentHeart.FindSenderNicknamePort;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;
import refrigerator.back.notification.application.port.out.notification.SaveNotificationPort;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CreateNotificationServiceTest {

    @InjectMocks CreateNotificationService createNotificationService;

    @Mock FindSenderNicknamePort findSenderNicknamePort;

    @Mock FindCommentDetailsPort commentDetailsPort;

    @Mock SaveNotificationPort saveNotificationPort;

    @Mock ModifyMemberNotificationPort modifyMemberNotificationPort;

    @Test
    @DisplayName("댓글 좋아요 알림 생성 전체 로직")
    void createCommentHeartNotificationTest() {

        String senderId = "email123@gmail.com";
        Long commentId = 1L;

        CommentNotificationDTO commentDto = CommentNotificationDTO.builder()
                .authorId("email456@gmail.com")
                .recipeId(1L)
                .build();

        given(findSenderNicknamePort.getNickname(senderId))
                .willReturn("익명1");

        given(commentDetailsPort.getDetails(1L))
                .willReturn(commentDto);

        willDoNothing().given(modifyMemberNotificationPort).modify(commentDto.getAuthorId(), true);

        given(saveNotificationPort.saveNotification(any()))
                .willReturn(1L);

        assertThat(createNotificationService.createCommentHeartNotification(senderId, commentId))
                .isEqualTo(1L);
    }

    @Test
    @DisplayName("댓글 좋아요 알림 생성")
    void madeNotificationTest() {
        Long commentId = 1L;

        CommentNotificationDTO commentDto = CommentNotificationDTO.builder()
                .authorId("email456@gmail.com")
                .recipeId(1L)
                .build();

        Notification notification = createNotificationService.madeNotification(commentId, commentDto);
        assertThat(notification.getType()).isEqualTo(NotificationType.HEART);
        assertThat(notification.getPath()).isEqualTo("/recipe/comment?recipeId=" + commentDto.getRecipeId() + "&commentID=" + commentId);
        assertThat(notification.getMemberId()).isEqualTo(commentDto.getAuthorId());
        assertThat(notification.getMethod()).isEqualTo(BasicHttpMethod.GET.name());
    }

}