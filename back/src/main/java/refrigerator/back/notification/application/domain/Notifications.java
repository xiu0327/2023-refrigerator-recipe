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

    @Column(name = "image")
    private String image;

    @Column(name = "message")
    private String message;

    @Column(name = "type")
    private String type;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "path")
    private String path;

    @Column(name = "read_status")
    private boolean readStatus; // true : 읽음 / false : 안 읽음

    @Column(name = "email")
    private String email;

    @Column(name = "method")
    private String method;

    public void isRead() {
        this.readStatus = true;
    }

    public void isNotRead() {
        this.readStatus = false;
    }

    public Notifications(String image, String message, String type, String path, String method, String email) {
        this.image = image;
        this.message = message;
        this.type = type;
        this.createTime = LocalDateTime.now();
        this.path = path;
        this.method = method;
        this.email = email;
    }

    public static Notifications create(String image, String message, String type, String path, String method, String email) {
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
        } else {
            throw new BusinessException(NotificationExceptionType.TEST_ERROR);
        }
    }

    // 테스트용 삭제
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
