package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.UpdatePasswordUseCase;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
public class MemberUpdatePasswordService implements UpdatePasswordUseCase {

    private final EncryptPasswordPort encryptPasswordPort;
    private final FindMemberPort findMemberPort;

    @Override
    @Transactional
    public String updatePassword(String email, String newPassword) {
        Member member = findMemberPort.findMemberNotUseCache(email);
        if (member == null){
            throw new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        String encryptNewPassword = encryptPasswordPort.encrypt(newPassword);
        member.isEqualPassword(encryptNewPassword);
        member.changePassword(encryptNewPassword);
        return member.getPassword();
    }

}
