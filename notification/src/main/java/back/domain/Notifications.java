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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean readStatus;

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
}
