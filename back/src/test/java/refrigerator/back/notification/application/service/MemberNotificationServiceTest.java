package refrigerator.back.notification.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class MemberNotificationServiceTest {

    @Autowired MemberNotificationService memberNotificationService;
    @Autowired CreateCommentHeartNotificationUseCase createCommentHeartNotificationUseCase;
    @Autowired TestData testData;

    @Test
    void 메시지_알림_신호_켜기() {
        String memberId = testData.createMemberByEmail("testEmail@gmail.com");
        memberNotificationService.SingOn(memberId);
        Assertions.assertThat(memberNotificationService.getSign(memberId)).isTrue();
    }

    @Test
    @DisplayName("메시지 알림 신호 켜기 -> 알림이 하나라도 왔을 때")
    void signOn() {
        // given
        String memberId = testData.createMemberByEmail("testEmail@gmail.com");
        String senderId = testData.createMemberByEmail("senderemail@gmail.com");
        Long commentId = testData.createComment(memberId).getCommentID();
        // when
        memberNotificationService.createMemberNotification(memberId);
        Assertions.assertThat(memberNotificationService.getSign(memberId)).isFalse();
        createCommentHeartNotificationUseCase.createCommentHeartNotification(senderId, commentId);
        // then
        Assertions.assertThat(memberNotificationService.getSign(memberId)).isTrue();
    }

    @Test
    @DisplayName(("메시지 알림 신호 끄기 -> 모든 알림을 읽었을 때"))
    void signOff() {
        // given
        String memberId = testData.createMemberByEmail("testEmail@gmail.com");
        String senderId = testData.createMemberByEmail("senderemail@gmail.com");
        Long commentId = testData.createComment(memberId).getCommentID();
        List<Long> ids = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++){
            Long result = createCommentHeartNotificationUseCase.createCommentHeartNotification(senderId, commentId);
            ids.add(result);
        }
    }

}