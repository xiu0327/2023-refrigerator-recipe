package refrigerator.back.notification.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.exception.NotificationExceptionType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "notification")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "image", nullable = false)
    private Integer image;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "path", nullable = false, length = 400)
    private String path;

    @Column(name = "read_status", nullable = false)
    private boolean readStatus; // true : 읽음 / false : 안 읽음

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "method", nullable = false, length = 30)
    private String method;

    public void isRead() {
        this.readStatus = true;
    }

    public void isNotRead() {
        this.readStatus = false;
    }

    public Notifications(Integer image, String message, Integer type, String path, String method, String email) {
        this.image = image;
        this.message = message;
        this.type = type;
        this.createTime = LocalDateTime.now();
        this.path = path;
        this.method = method;
        this.email = email;
    }

    // 테스트 용
    public Notifications(Integer image, String message, LocalDateTime createDate, Integer type, String path, String method, String email) {
        this.image = image;
        this.message = message;
        this.type = type;
        this.createTime = createDate;
        this.path = path;
        this.method = method;
        this.email = email;
    }

    public static Notifications create(Integer image, String message, Integer type, String path, String method, String email) {
        Notifications notification = new Notifications(image, message, type, path, method, email);
        notification.isNotRead();
        return notification;
    }

    // 리팩토링..
    public String getRegisterTime() {
        Long interval;
        if ((interval = ChronoUnit.YEARS.between(this.createTime, LocalDateTime.now())) > 0) {
            return interval + "년 전";
        } else if ((interval = ChronoUnit.MONTHS.between(this.createTime, LocalDateTime.now())) > 0) {
            return interval + "달 전";
        } else if ((interval = ChronoUnit.DAYS.between(this.createTime, LocalDateTime.now())) > 0) {
            return interval + "일 전";
        } else if ((interval = ChronoUnit.HOURS.between(this.createTime, LocalDateTime.now())) > 0) {
            return interval + "시간 전";
        } else if ((interval = ChronoUnit.MINUTES.between(this.createTime, LocalDateTime.now())) > 0) {
            return interval + "분 전";
        } else if ((interval = ChronoUnit.SECONDS.between(this.createTime, LocalDateTime.now())) > 0) {
            return interval + "초 전";
        } else if (ChronoUnit.MILLIS.between(this.createTime, LocalDateTime.now()) > 0) {
            return 0 + "초 전";
        } else {
            throw new BusinessException(NotificationExceptionType.TEST_ERROR);
        }
    }
}
