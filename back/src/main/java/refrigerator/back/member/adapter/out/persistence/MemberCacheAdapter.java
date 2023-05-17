package refrigerator.back.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.adapter.out.repository.MemberCacheRepository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.out.RenewMemberCachePort;

@Repository
@RequiredArgsConstructor
public class MemberCacheAdapter implements RenewMemberCachePort {

    private final MemberCacheRepository memberCacheRepository;

    @Override
    public void renew(Member member) {
        memberCacheRepository.updateCacheDate(member);
    }

}