package refrigerator.back.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.out.CreateMemberPort;
import refrigerator.back.member.application.port.out.FindMemberPort;
import refrigerator.back.member.application.port.out.UpdateMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MemberServiceTest {

    @Autowired TestData testData;
    @Autowired UpdateMemberPort updateMemberPort;

    @Test
    void 닉네임_수정() {
        String newNickname = "새닉네임";
        Member member = getMember();
        member.updateNickname(newNickname);
        updateMemberPort.update(member);
        System.out.println("MemberServiceTest.닉네임_수정.end");
        Member findMember = testData.findMemberByEmail(TestData.MEMBER_EMAIL);
        assertThat(findMember.getNickname()).isEqualTo(newNickname);
    }

    @Test
    void 프로필_수정_성공() {
        String newImageName = MemberProfileImage.PROFILE_IMAGE_FIVE.getName();
        Member member = getMember();
        member.updateProfile(newImageName);
        // 성공
        updateMemberPort.update(member);
        Member findMember = testData.findMemberByEmail(TestData.MEMBER_EMAIL);
        assertThat(findMember.getProfile().getName()).isEqualTo(newImageName);
    }

    @Test
    void 프로필_수정_실패() {
        // 실패
        assertThrows(BusinessException.class, () -> {
            try{
                String wrongProfileName = "없는사진";
                Member member = getMember();
                member.updateProfile(wrongProfileName);
                updateMemberPort.update(member);
            }catch (BusinessException e){
                log.info(e.getBasicExceptionType().getMessage()); // 해당 이미지를 찾을 수 없습니다.
                assertThat(e.getBasicExceptionType()).isEqualTo(MemberExceptionType.NOT_FOUND_PROFILE_IMAGE);
                throw e;
            }
        });
    }

    @Test
    void 회원_탈퇴_성공() {
        String inputPW = TestData.MEMBER_PASSWORD;
        Member member = getMember();
        member.withdraw(inputPW);
        updateMemberPort.update(member);
        Member findMember = testData.findMemberByEmail(TestData.MEMBER_EMAIL);
        assertThat(findMember.getMemberStatus()).isEqualTo(MemberStatus.LEAVE_STATUS);
    }

    @Test
    void 회원_탈퇴_실패() {
        assertThrows(BusinessException.class, () -> {
            try{
                String inputPW = "asdfas123";
                Member domain = getMember();
                domain.withdraw(inputPW);
                updateMemberPort.update(domain);
            }catch (BusinessException e){
                log.info(e.getBasicExceptionType().getMessage()); // 비밀번호가 일치하지 않습니다.
                assertThat(e.getBasicExceptionType()).isEqualTo(MemberExceptionType.NOT_EQUAL_PASSWORD);
                throw e;
            }
        });
    }

    private Member getMember() {
        testData.createMember();
        return testData.findMemberByEmail(TestData.MEMBER_EMAIL);
    }
}