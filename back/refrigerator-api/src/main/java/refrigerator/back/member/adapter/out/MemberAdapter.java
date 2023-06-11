package refrigerator.back.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.member.adapter.mapper.MemberDtoMapper;
import refrigerator.back.member.adapter.out.dto.MemberCacheDTO;
import refrigerator.back.member.adapter.out.repository.MemberCacheRepository;
import refrigerator.back.member.adapter.out.repository.MemberRepository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.PersistMemberPort;


@Repository
@RequiredArgsConstructor
public class MemberAdapter implements FindMemberPort, CreateMemberPort, PersistMemberPort {

    private final MemberRepository memberRepository;
    private final MemberCacheRepository memberCacheRepository;
    private final MemberDtoMapper mapper;

    @Override
    public Long createMember(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public Member findMember(String email) {
        MemberCacheDTO cacheData = memberCacheRepository.getCacheData(email);
        if (cacheData != null){
            return mapper.toMember(cacheData, cacheData.getJoinDateTime());
        }
        return null;
    }

    @Override
    public Member findMemberNotUseCache(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void persist(Member member) {
        memberRepository.save(member);
        memberCacheRepository.updateCacheDate(member);
    }

}
