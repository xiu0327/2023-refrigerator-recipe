package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.MemberDomain;

import java.util.Optional;

public interface FindMemberPort {
    MemberDomain findMember(String email);
}
