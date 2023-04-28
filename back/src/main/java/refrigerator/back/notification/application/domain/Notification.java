package refrigerator.back.notification.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.BaseTimeEntity;
import javax.persistence.*;

@Entity
@Table(name = "notification")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification extends BaseTimeEntity {

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

    public static Notification create(NotificationType type, String path, String memberId, String method){
        return Notification.builder()
                .type(type)
                .path(path)
                .method(method)
                .memberId(memberId).build();
    }

    public void setMessage(String senderNickname){
        this.message = senderNickname + " 님이 좋아요를 눌렀습니다.";
    }

    public boolean isReadStatus() {
        return readStatus;
    }
}
