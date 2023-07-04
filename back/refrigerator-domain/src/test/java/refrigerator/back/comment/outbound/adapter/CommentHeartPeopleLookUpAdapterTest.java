package refrigerator.back.comment.outbound.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.outbound.repository.redis.CommentHeartPeopleRedisRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest
class CommentHeartPeopleLookUpAdapterTest {

    @Autowired CommentHeartPeopleRedisRepository redisRepository;
    private FindCommentHeartPeoplePort commentHeartPeoplePort;

   @BeforeEach
   void init(){
       this.commentHeartPeoplePort = new CommentHeartPeopleLookUpAdapter(redisRepository);
       redisRepository.deleteAll();
   }

    @Test
    @DisplayName("사용자가 좋아요를 누른 댓글 조회")
    void findPeopleIdMap() {
        // given
        String memberId = "mstest102@gmail.com";
        saveTestData(memberId);
        // when
        Map<Long, Long> peoples = commentHeartPeoplePort.findPeopleMap(memberId);
        // then
        List<Long> expectedIds = Arrays.asList(1L, 2L, 3L);
        expectedIds.forEach(expectedId -> assertNotNull(peoples.getOrDefault(expectedId, null)));
        assertNull(peoples.getOrDefault(4L, null));
    }

    @Test
    @DisplayName("좋아요를 누른 회원 단건 조회")
    void findPeopleOne(){
       // given
        String memberId = "mstest102@gmail.com";
        saveTestData(memberId);
        // when
        Optional<CommentHeartPeople> result = commentHeartPeoplePort.findPeopleOne(2L, memberId);
        // then
        Assertions.assertTrue(result.isPresent());
    }

    private void saveTestData(String memberId){
        CommentHeartPeople people1 = new CommentHeartPeople(1L, memberId);
        CommentHeartPeople people2 = new CommentHeartPeople(2L, memberId);
        CommentHeartPeople people3 = new CommentHeartPeople(3L, memberId);
        redisRepository.saveAll(Arrays.asList(people1, people2, people3));
    }
}