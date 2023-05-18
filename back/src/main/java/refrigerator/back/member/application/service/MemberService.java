package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.*;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
public class MemberService implements FindMemberInfoUseCase {

    private final FindMemberPort findMemberPort;

    @Override
    @Transactional(readOnly = true)
    public Member findMember(String email) {
        Member member = findMemberPort.findMember(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        return member;
    }

    @Override
    @Transactional(readOnly = true)
    public Member pureFindMemberByEmail(String email) {
        return findMemberPort.findMemberNotUseCache(email);
    }


}
