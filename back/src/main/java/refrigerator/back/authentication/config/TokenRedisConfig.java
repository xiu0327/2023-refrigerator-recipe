package refrigerator.back.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class TokenRedisConfig {

    @Value("${spring.redis.token.host}")
    private String host;

    @Value("${spring.redis.token.port}")
    private int port;

    @Bean
    public RedisTemplate<String, String> tokenRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(tokenRedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory tokenRedisConnectionFactory(){
        return new LettuceConnectionFactory(host, port);
    }
}
