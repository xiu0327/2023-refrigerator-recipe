package refrigerator.back.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.back.global.TestData;
import refrigerator.back.member.adapter.out.entity.MemberEntity;
import refrigerator.back.member.adapter.out.mapper.MemberMapper;
import refrigerator.back.member.application.domain.MemberDomain;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberMapperTest {

    @Autowired TestData testData;
    @Autowired MemberMapper memberMapper;

    @Test
    void 도메인_To_엔티티(){
        MemberDomain domain = testData.createMemberDomain();
        MemberEntity entity = memberMapper.toMemberEntity(domain);

        // 모든 필드 값이 not null
        assertNotNull(entity.getId());
        assertNotNull(entity.getMemberStatus());
        assertNotNull(entity.getNickname());
        assertNotNull(entity.getEmail());
        assertNotNull(entity.getProfile());
        assertNotNull(entity.getPassword());
    }

    @Test
    void 엔티티_To_도메인(){
        MemberEntity entity = testData.createMemberEntity();
        MemberDomain domain = memberMapper.toMemberDomain(entity);

        // 모든 필드 값이 not null
        assertNotNull(domain.getMemberID());
        assertNotNull(domain.getMemberStatus());
        assertNotNull(domain.getNickname());
        assertNotNull(domain.getEmail());
        assertNotNull(domain.getProfile());
        assertNotNull(domain.getJoinDate());
        assertNotNull(domain.getPassword());
    }
}
