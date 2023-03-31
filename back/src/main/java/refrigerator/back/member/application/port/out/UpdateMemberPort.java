package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.Member;

public interface UpdateMemberPort {
    void update(Member member);
}
