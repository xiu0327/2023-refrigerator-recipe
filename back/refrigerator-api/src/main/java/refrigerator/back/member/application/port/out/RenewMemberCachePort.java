package refrigerator.back.member.application.port.out;


import refrigerator.back.member.application.domain.Member;

public interface RenewMemberCachePort {
    void renew(Member member);
}
