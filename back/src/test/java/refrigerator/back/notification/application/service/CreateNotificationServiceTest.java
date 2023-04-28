package refrigerator.back.notification.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;
import refrigerator.back.notification.application.port.out.read.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.read.FindNotificationPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class CreateNotificationServiceTest {

    @Autowired CreateCommentHeartNotificationUseCase createCommentHeartNotificationUseCase;
    @Autowired
    FindMemberNotificationSignPort findMemberNotificationStatusPort;
    @Autowired
    FindNotificationPort readNotificationPort;
    @Autowired TestData testData;

    @Test
    void 댓글_좋아요_알림_생성() {
        // given
        String authorId = testData.createMemberByEmail("asd123@naver.com");
        String senderId = testData.createMemberByEmail("asd456@naver.com");
        Long commentID = testData.createComment(authorId).getCommentID();
        // when
        Long notificationId = createCommentHeartNotificationUseCase.createCommentHeartNotification(senderId, commentID);
        // then
        Notification notification = readNotificationPort.getNotification(notificationId);
        log.info("notification message = {}", notification.getMessage());
        // 1. 알림이 DB에 제대로 저장되었는지
        assertThat(notification).isNotNull();
        // 2. 댓글 작성자 알림 표시가 되었는지
        assertThat(findMemberNotificationStatusPort.getSign(authorId)).isTrue();
    }
}