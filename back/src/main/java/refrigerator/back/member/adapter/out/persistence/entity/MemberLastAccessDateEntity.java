package refrigerator.back.member.adapter.out.persistence.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_last_access_date")
@Getter
public class MemberLastAccessDateEntity {

    @Id
    @Column(name = "member_last_access_date_id", nullable = false)
    private String dateID;

    @Column(name = "access_date", nullable = false)
    private LocalDateTime accessDate;

}
