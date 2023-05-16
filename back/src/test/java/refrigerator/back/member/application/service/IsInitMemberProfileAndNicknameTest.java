package refrigerator.back.member.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.adapter.out.dto.MemberCacheDTO;
import refrigerator.back.member.application.port.in.*;
import refrigerator.back.member.exception.MemberExceptionType;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class IsInitMemberProfileAndNicknameTest {

    @Autowired JoinUseCase joinUseCase;
    @Autowired InitNicknameAndProfileUseCase initNicknameAndProfileUseCase;
    @Autowired CheckFirstLoginUseCase checkFirstLoginUseCase;
    @Autowired UpdateProfileUseCase updateProfileUseCase;

    private final RedisTemplate<String, MemberCacheDTO> redisTemplate;

    public IsInitMemberProfileAndNicknameTest(
            @Qualifier("memberCacheRedisTemplate") RedisTemplate<String, MemberCacheDTO> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @AfterEach()
    void redisRollback(){
        redisTemplate.execute((RedisCallback<? extends Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }

    @Test
    @DisplayName("최초 로그인인지 확인 -> 최초 로그인일 때")
    void firstLoginMember(){
        String email = join();
        Assertions.assertThat(checkFirstLoginUseCase.checkFirstLogin(email)).isTrue();
    }

    @Test
    @DisplayName("최초 로그인인지 확인 -> 최초 로그인이 아닐 때 (닉네임/프로필 모두 설정되어 있음)")
    void notFirstLoginMember(){
        String email = join();
        initNicknameAndProfileUseCase.initNicknameAndProfile(email, "닉네임뿅", "IMG_9705.JPG");
        Assertions.assertThat(checkFirstLoginUseCase.checkFirstLogin(email)).isFalse();
    }

    @Test
    @DisplayName("최초 로그인인지 확인 -> 최초 로그인일 때 (닉네임 or 프로필만 설정되어 있음)")
    void notFirstLoginMember2(){
        String email = join();
        updateProfileUseCase.updateProfile(email, "IMG_9705.JPG");
        Assertions.assertThat(checkFirstLoginUseCase.checkFirstLogin(email)).isTrue();
    }

    @Test
    @DisplayName("닉네임/프로필 초기화 설정")
    void initMemberNicknameAndProfile(){
        String email = join();
        initNicknameAndProfileUseCase.initNicknameAndProfile(email, "수정닉네임", "IMG_9709.JPG");
        Assertions.assertThat(checkFirstLoginUseCase.checkFirstLogin(email)).isFalse();
    }

    @Test
    @DisplayName("닉네임/프로필 초기화 설정 실패 -> 닉네임 형식 오류")
    void initMemberNicknameAndProfileFail(){
        String email = join();
        assertThrows(BusinessException.class, () -> {
            try{
                initNicknameAndProfileUseCase.initNicknameAndProfile(email, "", "IMG_9709.JPG");
            }catch (BusinessException e){
                Assertions.assertThat(e.getBasicExceptionType()).isEqualTo(MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
                throw e;
            }
        });
        Assertions.assertThat(checkFirstLoginUseCase.checkFirstLogin(email)).isTrue();
    }

    private String join() {
        String email = "email123@gmail.com";
        String password = "password123!";
        joinUseCase.join(email, password, "");
        return email;
    }
}
