package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.MemberDomain;

public interface CreateMemberPort {
    Long createMember(MemberDomain domain);
}
