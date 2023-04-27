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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_notification_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "sign", nullable = false)
    private boolean sign;

    public void signOff() {
        this.sign = false;
    }

    public void signOn() {
        this.sign = true;
    }

    public static MemberNotification create(String email) {
        MemberNotification memberNotification = MemberNotification.builder().email(email).build();
        memberNotification.signOff();
        return memberNotification;
    }
}
