package refrigerator.back.member.adapter.out.mapper;

import org.springframework.stereotype.Component;
import refrigerator.back.member.adapter.out.entity.MemberEntity;
import refrigerator.back.member.application.domain.MemberDomain;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;

public class MemberMapperV1 implements MemberMapper{
    @Override
    public MemberDomain toMemberDomain(MemberEntity memberEntity) {
        return MemberDomain.builder()
                .memberID(memberEntity.getId())
                .email(memberEntity.getEmail())
                .joinDate(memberEntity.getJoinDate())
                .nickname(memberEntity.getNickname())
                .password(memberEntity.getPassword())
                .profile(MemberProfileImage.findImageByName(memberEntity.getProfile()))
                .memberStatus(MemberStatus.lookup(memberEntity.getMemberStatus()))
                .build();
    }

    @Override
    public MemberEntity toMemberEntity(MemberDomain memberDomain) {
        return MemberEntity.builder()
                .id(memberDomain.getMemberID())
                .email(memberDomain.getEmail())
                .password(memberDomain.getPassword())
                .nickname(memberDomain.getNickname())
                .memberStatus(memberDomain.getMemberStatus().getStatusCode())
                .joinDate(memberDomain.getJoinDate())
                .profile(memberDomain.getProfile().getName())
                .build();
    }
}
