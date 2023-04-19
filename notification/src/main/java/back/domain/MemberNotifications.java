package back.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_notification")
public class MemberNotifications {

    @Id @GeneratedValue
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
