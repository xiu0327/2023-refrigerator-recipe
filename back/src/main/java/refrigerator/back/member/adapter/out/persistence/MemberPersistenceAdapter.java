package refrigerator.back.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.adapter.out.entity.MemberEntity;
import refrigerator.back.member.adapter.out.mapper.MemberMapper;
import refrigerator.back.member.adapter.out.repository.MemberRepository;
import refrigerator.back.member.application.domain.MemberDomain;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements FindMemberPort, CreateMemberPort, UpdateMemberPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Long createMember(MemberDomain domain) {
        MemberEntity member = memberMapper.toMemberEntity(domain);
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public MemberDomain findMember(String email) {
        Optional<MemberEntity> member = memberRepository.findByEmail(email);
        return member.map(memberMapper::toMemberDomain).orElse(null);
    }

    @Override
    public void update(MemberDomain memberDomain) {
        MemberEntity member = memberMapper.toMemberEntity(memberDomain);
        memberRepository.save(member);
    }
}
