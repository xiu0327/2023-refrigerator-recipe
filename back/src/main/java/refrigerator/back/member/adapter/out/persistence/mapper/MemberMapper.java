package refrigerator.back.member.adapter.out.persistence.mapper;

import refrigerator.back.member.adapter.out.persistence.entity.MemberEntity;
import refrigerator.back.member.application.domain.MemberDomain;

public interface MemberMapper {
    MemberDomain toMemberDomain(MemberEntity memberEntity);
    MemberEntity toMemberEntity(MemberDomain memberDomain);

}
