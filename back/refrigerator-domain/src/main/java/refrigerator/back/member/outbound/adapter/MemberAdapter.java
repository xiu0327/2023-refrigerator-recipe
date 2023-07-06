package refrigerator.back.member.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.member.outbound.mapper.OutMemberMapper;
import refrigerator.back.member.outbound.repository.MemberCacheRepository;
import refrigerator.back.member.outbound.repository.jpa.MemberRepository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.PersistMemberPort;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class MemberAdapter implements FindMemberPort, CreateMemberPort, PersistMemberPort {

    private final MemberRepository memberRepository;
    private final MemberCacheRepository memberCacheRepository;
    private final OutMemberMapper mapper;

    @Override
    public Long createMember(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public void persist(Member member) {
        memberRepository.save(member);
        memberCacheRepository.updateCacheDate(member);
    }

}
