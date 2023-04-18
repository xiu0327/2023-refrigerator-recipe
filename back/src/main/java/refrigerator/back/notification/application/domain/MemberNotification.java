package refrigerator.back.notification.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member_notification")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberNotification {

    @Id @GeneratedValue
    @Column(name = "member_notification")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "sign")
    private boolean sign;

    public void signOff() {
        this.sign = false;
    }

    public void signOn() {
        this.sign = true;
    }
}
