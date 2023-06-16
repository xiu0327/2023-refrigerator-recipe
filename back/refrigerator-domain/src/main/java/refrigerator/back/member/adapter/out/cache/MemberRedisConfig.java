package refrigerator.back.member.adapter.out.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import refrigerator.back.member.adapter.out.dto.MemberCacheDTO;

@Configuration
public class MemberRedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    public MemberRedisConfig(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, MemberCacheDTO> memberCacheRedisTemplate() {
        RedisTemplate<String, MemberCacheDTO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
