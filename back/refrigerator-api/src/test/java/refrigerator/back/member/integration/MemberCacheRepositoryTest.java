package refrigerator.back.member.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.adapter.out.mapper.MemberDtoMapper;
import refrigerator.back.member.adapter.out.dto.MemberCacheDTO;
import refrigerator.back.member.adapter.out.repository.MemberCacheRepository;
import refrigerator.back.member.application.domain.Member;
import refrigerator.back.member.application.port.in.InitNicknameAndProfileUseCase;
import refrigerator.back.member.application.port.out.FindMemberPort;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
public class MemberCacheRepositoryTest {

    @Autowired FindMemberPort findMemberPort;
    @Autowired InitNicknameAndProfileUseCase initNicknameAndProfileUseCase;
    @Autowired MemberDtoMapper memberDtoMapper;
    @Autowired MemberCacheRepository memberCacheRepository;

    String email = "nhtest@gmail.com";


    @Test
    @DisplayName("동일한 요청을 반복했을 때, 캐시 데이터를 가져오는지 확인")
    void saveCacheData() throws InterruptedException {
        assertThat(findMemberPort.findMember(email)).isNotNull(); // select 1번
        Thread.sleep(1000 * 2);
        assertThat(findMemberPort.findMember(email)).isNotNull(); // select 호출 X
        assertThat(findMemberPort.findMember(email)).isNotNull(); // select 호출 X
    }

    @Test
    @DisplayName("member 와 memberCacheDTO 사이에 매핑이 제대로 이루어지는지 확인")
    void mappingTest(){
        Member member = findMemberPort.findMemberNotUseCache(email);
        MemberCacheDTO cacheDTO = memberDtoMapper.toMemberCacheDto(member);
        Member transMember = memberDtoMapper.toMember(cacheDTO, cacheDTO.getJoinDateTime());
        assertThat(member).isEqualTo(transMember);
    }

    @Test
    @DisplayName("캐시가 제대로 삭제되는지 확인")
    void checkCacheDelete(){
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
