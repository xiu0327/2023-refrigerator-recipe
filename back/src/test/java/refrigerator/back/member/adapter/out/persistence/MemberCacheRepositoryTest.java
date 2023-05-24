package refrigerator.back.member.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.member.adapter.mapper.MemberDtoMapper;
import refrigerator.back.member.adapter.out.dto.MemberCacheDTO;
import refrigerator.back.member.adapter.out.repository.MemberCacheRepository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.InitNicknameAndProfileUseCase;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.application.port.out.FindMemberPort;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
public class MemberCacheRepositoryTest {

    @Autowired FindMemberPort findMemberPort;
    @Autowired JoinUseCase joinUseCase;
    @Autowired InitNicknameAndProfileUseCase initNicknameAndProfileUseCase;
    @Autowired MemberDtoMapper memberDtoMapper;
    @Autowired MemberCacheRepository memberCacheRepository;
    @Autowired TestData testData;

    private final RedisTemplate<String, MemberCacheDTO> redisTemplate;

    public MemberCacheRepositoryTest(
            @Qualifier("memberCacheRedisTemplate") RedisTemplate<String, MemberCacheDTO> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @BeforeEach()
    void redisRollback(){
        redisTemplate.execute((RedisCallback<? extends Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }

    @Test
    @DisplayName("동일한 요청을 반복했을 때, 캐시 데이터를 가져오는지 확인")
    void saveCacheData() throws InterruptedException {
        String email = testData.createMemberByEmail("email@gmail.com");
        assertThat(findMemberPort.findMember(email)).isNotNull(); // select 1번
        Thread.sleep(1000 * 2);
        assertThat(findMemberPort.findMember(email)).isNotNull(); // select 호출 X
        assertThat(findMemberPort.findMember(email)).isNotNull(); // select 호출 X
    }

    @Test
    @DisplayName("member 와 memberCacheDTO 사이에 매핑이 제대로 이루어지는지 확인")
    void mappingTest(){
        String email = testData.createMemberByEmail("email@gmail.com");
        Member member = testData.findMemberByEmail(email);
        MemberCacheDTO cacheDTO = memberDtoMapper.toMemberCacheDto(member, member.getCreateDate());
        Member getMemberData = memberDtoMapper.toMember(cacheDTO, cacheDTO.getCreateDate());
        /*
        ------- 각 필드 값이 동일한지 확인 -------
         */
        assertThat(cacheDTO.getId()).isEqualTo(getMemberData.getId());
        assertThat(cacheDTO.getEmail()).isEqualTo(getMemberData.getEmail());
        assertThat(cacheDTO.getNickname()).isEqualTo(getMemberData.getNickname());
        assertThat(cacheDTO.getPassword()).isEqualTo(getMemberData.getPassword());
        assertThat(cacheDTO.getMemberStatus()).isEqualTo(getMemberData.getMemberStatus());
        assertThat(cacheDTO.getProfile()).isEqualTo(getMemberData.getProfile());
    }

    @Test
    @DisplayName("캐시가 제대로 삭제되는지 확인")
    void checkCacheDelete(){
        String email = "email@gmail.com";
        joinUseCase.join(email, "password123!", "");
        // 1. 회원 조회 -> 캐시 생성 (select 1)
        findMemberPort.findMember(email);
        assertThat(memberCacheRepository.getCacheData(email)).isNotNull();
        // 2. 회원 정보 변경 -> 캐시 삭제 (select 2)
        initNicknameAndProfileUseCase.initNicknameAndProfile(email, "임시 닉네임", "IMG_9709.JPG");
        memberCacheRepository.deleteCacheDate(email);
        // 3. 캐시가 삭제 -> 업데이트 된 내용이 조회 됨 (select 3)
        MemberCacheDTO cacheData = memberCacheRepository.getCacheData(email);
        assertThat(cacheData.getNickname()).isEqualTo("임시 닉네임");
    }

}