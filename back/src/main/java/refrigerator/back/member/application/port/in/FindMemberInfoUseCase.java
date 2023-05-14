package refrigerator.back.member.application.port.in;

import refrigerator.back.member.application.domain.Member;

public interface FindMemberInfoUseCase {
    Member findMember(String email);
    Member pureFindMemberByEmail(String email);
}
