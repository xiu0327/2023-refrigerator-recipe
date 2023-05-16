package refrigerator.back.member.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.TestData;
import refrigerator.back.member.application.port.in.FindMemberInfoUseCase;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberCacheTest {

    @Autowired TestData testData;
    @Autowired FindMemberInfoUseCase findMemberInfoUseCase;

    @Test
    @DisplayName("캐시가 제대로 적용이 된다면, 반복 요청을 했을 때 쿼리문이 발생하지 않음")
    void findMember() {
        String email = testData.createMemberByEmail("email@gamil.com");
        findMemberInfoUseCase.findMember(email);
        findMemberInfoUseCase.findMember(email);
        findMemberInfoUseCase.findMember(email);
        findMemberInfoUseCase.findMember(email);
    }
}