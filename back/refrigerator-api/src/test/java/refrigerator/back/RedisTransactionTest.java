package refrigerator.back;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.annotation.RedisFlushAll;
import refrigerator.back.member.application.domain.Member;

import javax.persistence.EntityManager;

@SpringBootTest
@RedisFlushAll(beanName = "tokenRedisTemplate")
@Disabled
public class RedisTransactionTest {

    @Autowired
    @Qualifier("tokenRedisTemplate")
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    EntityManager entityManager;


    @Test
    @DisplayName("Redis 트랜잭션 테스트")
    void redisTransactionTest(){
        redisTemplate.execute(new SessionCallback<String>() {
            @Override
            public <K, V> String execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.multi(); // transaction 시작
                redisTemplate.opsForValue().set("test", "value");
                operations.exec();
                String result = redisTemplate.opsForValue().get("test");
                Assertions.assertThat(result).isEqualTo("value");
                return null;
            }
        });
    }

    @Test
    @DisplayName("MySQL 트랜잭션 테스트")
    @Transactional
    void mysqlTransactionTest(){
        String email = "email123@gmail.com";
        Member member = Member.join(email, "password123!", "닉네임");
        entityManager.persist(member);
        Member findMember = entityManager.createQuery("select m from Member m where m.email= :email", Member.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findAny().get();
        Assertions.assertThat(member).isEqualTo(findMember);
    }


    @Test
    @DisplayName("RedisRollback 어노테이션 테스트")
     void annotationTest(){
        System.out.println("RedisTransactionTest.annotationTest");
    }


}
