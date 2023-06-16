package refrigerator.back.authentication.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.in.IssueTemporaryAccessTokenUseCase;
import refrigerator.back.authentication.application.port.out.CreateTokenPort;
import refrigerator.back.global.exception.domain.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueTemporaryTokenService implements IssueTemporaryAccessTokenUseCase {

    private final FindMemberPort findMemberPort;
    private final CreateTokenPort createTokenPort;

    @Override
    public String issueTemporaryAccessToken(String email) {
        Member member = findMemberPort.findMember(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        return createTokenPort.createTokenWithDuration(
                email,
                member.getMemberStatus().getStatusCode(),
                1000 * 60 * 10);
    }
}
