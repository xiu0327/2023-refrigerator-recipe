package refrigerator.back.authentication.infra.redis;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import refrigerator.back.global.config.redis.BusinessRedisLettuceConnectionConfig;

@Configuration
@Import(BusinessRedisLettuceConnectionConfig.class)
public class RefreshTokenRedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    public RefreshTokenRedisConfig(@Qualifier("businessRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Boolean> refreshTokenRedisTemplate(){
        RedisTemplate<String, Boolean> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new TokenPrefixedKeySerializer());
        redisTemplate.setHashKeySerializer(new TokenPrefixedKeySerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
