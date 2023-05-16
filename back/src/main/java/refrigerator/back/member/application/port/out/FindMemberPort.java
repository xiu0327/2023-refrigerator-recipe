package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.Member;

import java.util.Optional;

public interface FindMemberPort {
    Member findMember(String email);
    Member findMemberNotUseCache(String email);
}
