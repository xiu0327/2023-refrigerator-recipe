package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.MemberDomain;

public interface UpdateMemberPort {
    void update(MemberDomain memberDomain);
}
