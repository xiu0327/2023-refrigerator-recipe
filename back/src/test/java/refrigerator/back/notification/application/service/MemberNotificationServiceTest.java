package refrigerator.back.notification.application.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;
import refrigerator.back.notification.application.port.in.UpdateMemberNotificationSignUseCase;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
class MemberNotificationServiceTest {

    @Autowired MemberNotificationService memberNotificationService;
    @Autowired CreateCommentHeartNotificationUseCase createCommentHeartNotificationUseCase;
    @Autowired
    UpdateMemberNotificationSignUseCase updateMemberNotificationSignUseCase;
    @Autowired TestData testData;

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
    @DisplayName("메시지 알림 신호 끄기 -> 알림 아이콘을 눌렀을 때")
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
        Boolean before = memberNotificationService.getSign(memberId);
        // when
        updateMemberNotificationSignUseCase.updateNotification(memberId);
        // then
        Boolean after = memberNotificationService.getSign(memberId);
        Assertions.assertThat(before).isTrue();
        Assertions.assertThat(after).isFalse();
    }
}