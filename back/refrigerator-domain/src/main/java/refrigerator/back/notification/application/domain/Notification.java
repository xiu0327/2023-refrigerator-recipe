package refrigerator.back.notification.application.domain;

import lombok.*;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.exception.NotificationExceptionType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "path", nullable = false, length = 400)
    private String path;

    @Column(name = "read_status", nullable = false)
    private boolean readStatus; // true : 읽음 / false : 안 읽음

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "method", nullable = false, length = 30)
    private String method;

    @Column(name = "create_date", nullable = false, length = 30)
    private LocalDateTime createDate;

    public static Notification create(NotificationType type, String path, String memberId, String method, LocalDateTime createDate){
        return Notification.builder()
                .type(type)
                .path(path)
                .method(method)
                .memberId(memberId)
                .createDate(createDate)
                .build();
    }

    public void createCommentHeartMessage(String senderNickname){
        this.message = senderNickname + " 님이 좋아요를 눌렀습니다.";
    }

    public void createExpirationDateMessage(String name, Long count, Integer days){
        if(count < 1)
            throw new BusinessException(NotificationExceptionType.TEST_ERROR);
        else if(count > 1)
            this.message = name + " 외 "+ (count - 1) + "개 식재료의 소비기한이 " + days + "일 남았습니다. 식재료 확인하러가기!";
        else
            this.message = name + "의 소비기한이 " + days + "일 남았습니다. 식재료 확인하러가기!";
    }

    public void createIngredientMessage(String name) {
        this.message = "회원님이 요청했던 " + name + "를 이제 냉장고에 담을 수 있습니다. (식재료 추가하기)";
    }

    public void createNoticeMessage(String title) {
        this.message = "공지사항이 추가되었어요! '" + title + "'";
    }
}
