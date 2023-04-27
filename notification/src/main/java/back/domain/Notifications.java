package back.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

    public static Notifications create(Integer image, String message, Integer type, String path, String method, String email) {
        Notifications notification = new Notifications(image, message, type, path, method, email);
        notification.isNotRead();
        return notification;
    }
}
