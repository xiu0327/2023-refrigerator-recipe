package refrigerator.back.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.service.MemberAccessService;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MemberAccessServiceTest {

    @Autowired TestData testData;
    @Autowired
    MemberAccessService memberAccessService;

    @Test
    void 회원_가입(){
        String email = TestData.MEMBER_EMAIL;
        Long memberID = memberAccessService.join(email, TestData.MEMBER_PASSWORD, TestData.MEMBER_NICKNAME);
        Member member = testData.findMemberByEmail(email);
        log.info(member.getProfile().getName());
        assertThat(member.getProfile()).isNotNull();
        assertThat(memberID).isEqualTo(member.getId());
    }

    @Test
    void 등록된_회원_인지(){
        String wrongEmail = "email1@naver.com";
        testData.createMember();
        // 성공
        memberAccessService.findPassword(TestData.MEMBER_EMAIL);
        // 실패
        assertThrows(BusinessException.class, () -> {
            try{
                memberAccessService.findPassword(wrongEmail);
            }catch (BusinessException e){
                log.info(e.getBasicExceptionType().getMessage());
                assertThat(e.getBasicExceptionType().getErrorCode()).isEqualTo(MemberExceptionType.NOT_FOUND_MEMBER.getErrorCode());
                throw e;
            }
        });
    }

    @Test
    void 비밀번호_수정(){
        String newPassword = "newpasswod123!";
        testData.createMember();
        memberAccessService.updatePassword(TestData.MEMBER_EMAIL, newPassword);
        Member findMember = testData.findMemberByEmail(TestData.MEMBER_EMAIL);
        assertThat(findMember.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void 회원_중복(){
        testData.createMember();
        assertThrows(BusinessException.class, () -> {
            try{
                memberAccessService.duplicateCheck(TestData.MEMBER_EMAIL);
            }catch(BusinessException e){
                log.info(e.getBasicExceptionType().getMessage());
                assertThat(e.getBasicExceptionType().getErrorCode()).isEqualTo(MemberExceptionType.DUPLICATE_EMAIL.getErrorCode());
                throw e;
            }
        });
    }

}