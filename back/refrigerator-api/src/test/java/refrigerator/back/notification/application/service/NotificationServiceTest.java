package refrigerator.back.notification.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;
import refrigerator.back.notification.application.port.out.read.FindNotificationPort;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NotificationServiceTest {

    @Autowired TestData testData;
    @Autowired NotificationService notificationService;
    @Autowired CreateCommentHeartNotificationUseCase createCommentHeartNotificationUseCase;
    @Autowired FindNotificationPort readNotificationPort;

    @Test
    void 알림_조회() {
        // given
        String memberId = testData.createMemberByEmail("testEmail@gmail.com");
        String senderId = testData.createMemberByEmail("senderemail@gmail.com");
        Long commentId = testData.createComment(memberId).getCommentID();
        // when
        createCommentHeartNotificationUseCase.createCommentHeartNotification(senderId, commentId);
        // then
        List<NotificationDTO> result = notificationService.getNotificationList(memberId, 0, 10);
        for (NotificationDTO dto : result) {
            Assertions.assertNotNull(dto.getMessage());
            Assertions.assertNotNull(dto.getPath());
            Assertions.assertNotNull(dto.getType());
            Assertions.assertNotNull(dto.getRegisterTime());
        }
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void 알림_조회_최근순_정렬() throws InterruptedException {
        // given
        String memberId = testData.createMemberByEmail("testEmail@gmail.com");
        String senderId = testData.createMemberByEmail("senderemail@gmail.com");
        Long commentId = testData.createComment(memberId).getCommentID();
        // when
        createCommentHeartNotificationUseCase.createCommentHeartNotification(senderId, commentId);
        Thread.sleep(5000);
        createCommentHeartNotificationUseCase.createCommentHeartNotification(senderId, commentId);
        // then
        List<NotificationDTO> result = notificationService.getNotificationList(memberId, 0, 10);
        int first = Integer.parseInt(result.get(0).getRegisterTime().split(" ")[0]);
        int second = Integer.parseInt(result.get(1).getRegisterTime().split(" ")[0]);
        assertThat(first < second).isTrue();
    }

    @Test
    void 알림_읽기() {
        // given
        String memberId = testData.createMemberByEmail("testEmail@gmail.com");
        String senderId = testData.createMemberByEmail("senderemail@gmail.com");
        Long commentId = testData.createComment(memberId).getCommentID();
        // when
        Long notificationId = createCommentHeartNotificationUseCase.createCommentHeartNotification(senderId, commentId);
        boolean before = readNotificationPort.getNotification(notificationId).isReadStatus();
        notificationService.readNotification(notificationId);
        boolean after = readNotificationPort.getNotification(notificationId).isReadStatus();
        //then
        assertThat(before).isFalse();
        assertThat(after).isTrue();
    }
}