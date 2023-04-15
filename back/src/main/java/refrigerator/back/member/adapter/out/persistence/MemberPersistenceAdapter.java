package refrigerator.back.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.adapter.out.repository.MemberRepository;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements FindMemberPort, CreateMemberPort, UpdateMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public Long createMember(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public Member findMember(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.orElse(null);
    }

    @Override
    public void update(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void updateNickname(String email, String nickname) {
        memberRepository.updateNickname(email, nickname);
    }

    @Override
    public void updateProfile(String email, MemberProfileImage profile) {
        memberRepository.updateProfile(email, profile);
    }
}
