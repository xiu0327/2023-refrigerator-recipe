package refrigerator.back.notification.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.adapter.out.persistence.CommentPersistenceAdapter;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.member.application.service.MemberAccessService;
import refrigerator.back.notification.adapter.in.dto.NotificationResponseDTO;
import refrigerator.back.notification.adapter.mapper.NotificationMapper;
import refrigerator.back.notification.adapter.out.persistence.NotificationAdapter;
import refrigerator.back.notification.application.domain.MemberNotification;
import refrigerator.back.notification.application.domain.Notifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class NotificationServiceTest {

    @Autowired NotificationService notificationService;
    @Autowired NotificationAdapter notificationAdapter;
    @Autowired CommentPersistenceAdapter commentPersistenceAdapter;
    @Autowired NotificationMapper notificationMapper;
    @Autowired MemberAccessService memberAccessService;

    /**
     * 알림 생성 (댓글 알림 등등) : 알림 생성(isNotRead), 멤버 알림 수정(on)
     * 알림 목록 조회시 : 알림 목록 조회, 멤버 알림 수정(off)
     * 알림 수정 : 알림 목록에서 요소를 클릭 -> 알림 읽음 상태를 변경하는 것 (isRead)
     * 멤버 알림 생성 : 회원가입 -> 멤버 알림 생성 (off)
     * 멤버 알림 조회 : 종 옆에 빨간점의 여부 실시간 확인
     */

    @Test
    void 댓글_좋아요_알림_생성() {

        String memberId = "asd123@naver.com";
        String anotherId = "asd456@naver.com";

        // 댓글 생성
        Long CommentId = commentPersistenceAdapter.persist(Comment.write(1L, memberId, "내용"));
        Comment comment = commentPersistenceAdapter.findCommentById(CommentId).orElse(null);

        // 멤버 알림 생성
        notificationAdapter.saveMemberNotification(MemberNotification.create(memberId));
        assertThat(notificationService.getNotificationSign(memberId)).isFalse();

        // 알림 생성
        Long notificationId = notificationService.createCommentHeartNotification(anotherId, comment);

        Notifications notification = notificationAdapter.getNotification(notificationId);
        assertThat(notification.getPath()).isEqualTo("localhost:8080/api/recipe/" + comment.getRecipeID());
        assertThat(notification.getMessage()).isEqualTo(anotherId + "님이 " + memberId + "님의 댓글을 좋아합니다.");
        assertThat(notification.getEmail()).isEqualTo(memberId);

        assertThat(notificationService.getNotificationSign(memberId)).isTrue();

    }

    @Test
    void 알림_생성() {

        Long notificationId = notificationAdapter.saveNotification(
                Notifications.create(4, "안녕하세요 뿌꾸뿌꾸빵", 4,
                        "localhost:8080", "GET", "asd123@naver.com"));

        Notifications notification = notificationAdapter.getNotification(notificationId);
        assertThat(notification.getMessage()).isEqualTo("안녕하세요 뿌꾸뿌꾸빵");
        assertThat(notification.getPath()).isEqualTo("localhost:8080");
        assertThat(notification.getEmail()).isEqualTo("asd123@naver.com");
    }

    @Test
    void 알림_목록_조회() {

        String email = "asd123@naver.com";

        // 멤버 알림 생성
        notificationAdapter.saveMemberNotification(MemberNotification.create(email));

        // 알림 생성 (
        for (int i = 0; i < 15; i++) {
            notificationAdapter.saveNotification(
                    Notifications.create((i % 4) + 1, "안녕하세요 뿌꾸뿌꾸빵" + i + 1, (i % 4) + 1,
                            "localhost:8080", "GET", email));
        }

        for (int i = 0; i < 3; i++) {
            List<NotificationResponseDTO> notificationList = notificationService.getNotificationList(email, i, 5);
            for (NotificationResponseDTO dto : notificationList) {
                log.info(dto.toString());
            }
        }
    }

    @Test
    void 알림_조회_시간_변환_테스트() {

        String email = "asd123@naver.com";
        List<Long> ids = new ArrayList<>();

        // 멤버 알림 생성
        notificationAdapter.saveMemberNotification(MemberNotification.create(email));

        // 알림 생성
        ids.add(notificationAdapter.saveNotification(new Notifications(1, "안녕하세요 뿌꾸뿌꾸빵",
                LocalDateTime.now().minusYears(5), 1, "localhost:8080", "GET", email)));

        ids.add(notificationAdapter.saveNotification(new Notifications(1, "안녕하세요 뿌꾸뿌꾸빵",
                LocalDateTime.now().minusMonths(3), 1, "localhost:8080", "GET", email)));

        ids.add(notificationAdapter.saveNotification(new Notifications(1, "안녕하세요 뿌꾸뿌꾸빵",
                LocalDateTime.now().minusDays(25), 1, "localhost:8080", "GET", email)));

        ids.add(notificationAdapter.saveNotification(new Notifications(1, "안녕하세요 뿌꾸뿌꾸빵",
                LocalDateTime.now().minusHours(3), 1, "localhost:8080", "GET", email)));

        ids.add(notificationAdapter.saveNotification(new Notifications(1, "안녕하세요 뿌꾸뿌꾸빵",
                LocalDateTime.now().minusMinutes(20), 1, "localhost:8080", "GET", email)));

        ids.add(notificationAdapter.saveNotification(new Notifications(1, "안녕하세요 뿌꾸뿌꾸빵",
                LocalDateTime.now().minusSeconds(15), 1, "localhost:8080", "GET", email)));

        ids.add(notificationAdapter.saveNotification(new Notifications(1, "안녕하세요 뿌꾸뿌꾸빵",
                LocalDateTime.now(), 1, "localhost:8080", "GET", email)));

        ids.add(notificationAdapter.saveNotification(new Notifications(1, "안녕하세요 뿌꾸뿌꾸빵",
                LocalDateTime.now(), 1, "localhost:8080", "GET", email)));

        // 알림 => dto로 변경
        List<NotificationResponseDTO> dto = new ArrayList<>();

        for (Long id : ids) {
            Notifications notification = notificationAdapter.getNotification(id);
            dto.add(notificationMapper.toNotificationResponseDTO(notification));
        }
        
        assertThat(dto.get(0).getRegisterTime()).isEqualTo("5년 전");
        assertThat(dto.get(1).getRegisterTime()).isEqualTo("3달 전");
        assertThat(dto.get(2).getRegisterTime()).isEqualTo("25일 전");
        assertThat(dto.get(3).getRegisterTime()).isEqualTo("3시간 전");
        assertThat(dto.get(4).getRegisterTime()).isEqualTo("20분 전");
        assertThat(dto.get(5).getRegisterTime()).isEqualTo("15초 전");
        assertThat(dto.get(6).getRegisterTime()).isEqualTo("0초 전");
    }

    @Test
    void 알림_수정_안읽음_to_읽음 () {

        Long id = notificationAdapter.saveNotification(Notifications.create(1, "안녕하세요 뿌꾸뿌꾸빵", 1,
                "localhost:8080", "GET", "asd123@naver.com"));

        assertThat(notificationAdapter.getNotification(id).isReadStatus()).isFalse();

        notificationService.modifyNotification(id);

        assertThat(notificationAdapter.getNotification(id).isReadStatus()).isTrue();

    }

    @Test
    void 멤버_알림_생성_회원가입 () {

        memberAccessService.join("asd123@naver.com", "asdasd12312!!", "닉네임얍");
        assertThat(notificationService.getNotificationSign("asd123@naver.com")).isFalse();
    }

    @Test
    void 멤버_알림_조회_from_신규_알림 () {

        String memberId = "asd123@naver.com";
        String anotherId = "asd456@naver.com";

        // 멤버 알림 생성
        notificationAdapter.saveMemberNotification(MemberNotification.create(memberId));
        assertThat(notificationService.getNotificationSign(memberId)).isFalse();

        // 댓글 생성
        Long CommentId = commentPersistenceAdapter.persist(Comment.write(1L, memberId, "내용"));
        Comment comment = commentPersistenceAdapter.findCommentById(CommentId).orElse(null);

        // 알림 생성
        Long notificationId = notificationService.createCommentHeartNotification(anotherId, comment);

        assertThat(notificationService.getNotificationSign(memberId)).isTrue();

        notificationService.getNotificationList(memberId, 0, 5);

        assertThat(notificationService.getNotificationSign(memberId)).isFalse();
    }
}