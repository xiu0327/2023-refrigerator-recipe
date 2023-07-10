package refrigerator.back.member.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.outbound.repository.jpa.MemberRepository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.out.CreateMemberPort;


@Repository
@RequiredArgsConstructor
public class MemberCreateAdapter implements CreateMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public Long createMember(Member member) {
        memberRepository.save(member);
        return member.getId();
    }


}
