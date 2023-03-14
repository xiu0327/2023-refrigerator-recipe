package refrigerator.back.member.adapter.out.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member_authority")
@Getter
public class MemberAuthorityEntity {

    @Id
    @Column(name = "authority_name", length = 50, nullable = false)
    private String authorityName;

}
