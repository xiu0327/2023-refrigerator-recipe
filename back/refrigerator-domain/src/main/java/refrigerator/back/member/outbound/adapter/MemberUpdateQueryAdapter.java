package refrigerator.back.member.outbound.adapter;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.member.outbound.repository.MemberCacheRepository;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.out.ModifyMemberNicknamePort;
import refrigerator.back.member.application.port.out.ModifyMemberProfilePort;


import javax.persistence.EntityManager;

import static refrigerator.back.member.application.domain.QMember.member;

@Component
@RequiredArgsConstructor
public class MemberUpdateQueryAdapter implements ModifyMemberNicknamePort, ModifyMemberProfilePort {

    private final JPAQueryFactory jpaQueryFactory;
    private final MemberCacheRepository memberCacheRepository;
    private final EntityManager em;


    @Override
    public void modifyNickname(String email, String nickname) {
        jpaQueryFactory.update(member)
                .set(member.nickname, nickname)
                .where(member.email.eq(email))
                .execute();
        clearCache(email);
    }

    @Override
    public void modifyProfile(String email, MemberProfileImage profile) {
        jpaQueryFactory.update(member)
                .set(member.profile, profile)
                .where(member.email.eq(email))
                .execute();
        clearCache(email);
    }

    private void clearCache(String email) {
        em.flush();
        em.clear();
        memberCacheRepository.deleteCacheDate(email);
    }

}
