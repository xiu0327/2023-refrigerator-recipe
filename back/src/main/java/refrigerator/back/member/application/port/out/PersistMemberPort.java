package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.Member;

public interface PersistMemberPort {
    void persist(Member member);
}
