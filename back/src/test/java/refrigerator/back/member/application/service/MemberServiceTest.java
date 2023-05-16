package refrigerator.back.member.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.application.port.out.EncryptPasswordPort;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.in.CheckFirstLoginUseCase;
import refrigerator.back.member.application.port.in.FindMemberInfoUseCase;
import refrigerator.back.member.application.port.in.InitNicknameAndProfileUseCase;
import refrigerator.back.member.application.port.in.WithdrawMemberUseCase;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired WithdrawMemberUseCase withdrawMemberUseCase;
    @Autowired EncryptPasswordPort encryptPasswordPort;
    @Autowired TestData testData;

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawMember() {
        String password = "password123!";
        String email = testData.createMemberByEmailAndPassword("email@gmail.com", encryptPasswordPort.encrypt(password));
        withdrawMemberUseCase.withdrawMember(email);
        Assertions.assertThat(testData.findMemberByEmail(email).getMemberStatus()).isEqualTo(MemberStatus.LEAVE_STATUS);
    }

}