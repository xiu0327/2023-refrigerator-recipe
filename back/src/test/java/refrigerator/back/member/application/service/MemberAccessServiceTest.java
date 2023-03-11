package refrigerator.back.member.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MemberAccessServiceTest {

    @Autowired TestData testData;
    @Autowired MemberAccessService memberAccessService;

    @Test
    void 회원_가입(){
        String email = TestData.MEMBER_EMAIL;
        Long memberID = memberAccessService.join(email, TestData.MEMBER_PASSWORD, TestData.MEMBER_NICKNAME);
        assertThat(memberID).isEqualTo(testData.findMemberByEmail(email).getId());
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