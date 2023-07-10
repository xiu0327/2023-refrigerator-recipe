package refrigerator.back.member.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.domain.MemberProfileImage;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, MemberUpdateQueryRepository.class})
@TestDataInit("/member.sql")
class MemberUpdateQueryRepositoryTest {

    @Autowired MemberUpdateQueryRepository queryRepository;

    @Test
    @DisplayName("닉네임 수정 쿼리 성공 테스트")
    void updateNicknameByEmail() {
        // given
        String email = "mstest102@gmail.com";
        String nickname = "닉네임";
        // when
        WriteQueryResultType result = queryRepository.updateNicknameByEmail(email, nickname);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
    }

    @Test
    @DisplayName("닉네임 수정 쿼리 실패 테스트")
    void updateNicknameByEmailFailTest() {
        // given
        String email = "wrong@gmail.com";
        String nickname = "닉네임";
        // when
        WriteQueryResultType result = queryRepository.updateNicknameByEmail(email, nickname);
        // then
        assertEquals(WriteQueryResultType.FAIL, result);
    }

    @Test
    @DisplayName("비밀번호 수정 쿼리 성공 테스트")
    void updatePasswordByEmail() {
        // given
        String email = "mstest102@gmail.com";
        String password = "password";
        // when
        WriteQueryResultType result = queryRepository.updatePasswordByEmail(email, password);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
    }

    @Test
    @DisplayName("프로필 수정 쿼리 성공 테스트")
    void updateProfileImageByEmail() {
        // given
        String email = "mstest102@gmail.com";
        MemberProfileImage profileImage = MemberProfileImage.PROFILE_IMAGE_ONE;
        // when
        WriteQueryResultType result = queryRepository.updateProfileImageByEmail(email, profileImage);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
    }
}