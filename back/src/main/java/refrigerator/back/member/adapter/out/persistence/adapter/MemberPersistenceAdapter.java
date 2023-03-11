package refrigerator.back.member.adapter.out.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.adapter.out.persistence.entity.MemberEntity;
import refrigerator.back.member.adapter.out.persistence.mapper.MemberMapper;
import refrigerator.back.member.adapter.out.persistence.repository.MemberRepository;
import refrigerator.back.member.application.domain.MemberDomain;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements FindMemberPort, CreateMemberPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public Long createMember(MemberDomain domain) {
        MemberEntity member = memberMapper.toMemberEntity(domain);
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDomain findMember(String email) {
        Optional<MemberEntity> result = memberRepository.findByEmail(email);
        if (result.isPresent()){
            return memberMapper.toMemberDomain(result.get());
        }
        return null;
    }
}
