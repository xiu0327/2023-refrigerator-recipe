package refrigerator.back.member.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import refrigerator.back.member.adapter.out.persistence.entity.MemberEntity;
import refrigerator.back.member.application.domain.MemberDomain;
import refrigerator.back.member.application.domain.MemberRole;
import refrigerator.back.member.application.domain.MemberStatus;

@Component
public class MemberMapperV1 implements MemberMapper{
    @Override
    public MemberDomain toMemberDomain(MemberEntity memberEntity) {
        return MemberDomain.builder()
                .memberID(memberEntity.getId())
                .email(memberEntity.getEmail())
                .memberRole(MemberRole.lookup(memberEntity.getMemberRole()))
                .joinDate(memberEntity.getJoinDate())
                .nickname(memberEntity.getNickname())
                .password(memberEntity.getPassword())
                .profile(memberEntity.getProfile())
                .memberStatus(MemberStatus.lookup(memberEntity.getMemberStatus()))
                .build();
    }

    @Override
    public MemberEntity toMemberEntity(MemberDomain memberDomain) {
        return MemberEntity.builder()
                .email(memberDomain.getEmail())
                .password(memberDomain.getPassword())
                .nickname(memberDomain.getNickname())
                .memberStatus(memberDomain.getMemberStatus().getStatusCode())
                .build();
    }
}
