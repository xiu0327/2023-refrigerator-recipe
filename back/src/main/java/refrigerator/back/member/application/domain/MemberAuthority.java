package refrigerator.back.member.application.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member_authority")
@Getter
public class MemberAuthority {

    @Id
    @Column(name = "authority_name", length = 50, nullable = false)
    private String authorityName;

}
